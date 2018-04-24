package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.e3mall.cart.core.BaseController;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.manager.pojo.TbItem;
import cn.e3mall.manager.pojo.TbUser;

/**
 * 购物车处理Controller
 *
 * @author colg
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

	/** 购物车列表在cookie中存放的名字 */
	@Value("${COOKIE_CART}")
	private String cookieCart;
	/** 购物车列表在cookie中保存时间, 单位:秒 3600*24*5=43200 5天 */
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer cookieCartExpire;

	/**
	 * 添加商品到购物车, 返回购物车添加成功页面
	 * 
	 * @param itemId
	 *            商品id
	 * @param num
	 *            商品数量
	 * @return 购物车页面
	 */
	@GetMapping("/add/{itemId}")
	private String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 	1. 判断用户是否登录
		 * 		登录状态: 把购物车写入redis, 保存到服务端, 返回逻辑视图
		 * 		未登录:
		 *         	从cookie中取得购物车列表
		 *         		判断商品是否存在:
		 *         			存在: 	数量相加
		 *         			不存在:	根据商品id查询商品信息, 得到tbItem对象
		 *         					把商品添加到商品列表, 写入cookie, 保存到客户端, 返回添加成功页面
		 */
		// 判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("user");
		if (tbUser != null) {
			// 登录状态, 把购物车写入redis, 保存到服务端
			cartService.addCart(tbUser.getId(), itemId, num);
			return "cartSuccess";
		}

		// 用户未登录, 把购物车写入cookie, 保存到客户端
		List<TbItem> cartList = this.getCartListFromCookie(request);
		// 标识是存在商品
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			// 包装类型互相比较, 必须转成基础类型, 有一方为基础类型,另一方会自动拆箱
			if (tbItem.getId() == itemId.longValue()) {
				flag = true;
				// 存在商品, 数量相加
				tbItem.setNum(tbItem.getNum() + num);
				// 根据id找,只可能存在一个商品,跳出循环
				break;
			}
		}

		// 不存在
		if (!flag) {
			TbItem tbItem = tbItemService.getTbItemById(itemId);
			// 设置商品数量
			tbItem.setNum(num); 
			// 取得第一张图片
			String image = tbItem.getImage();
			if (StringUtils.isNotBlank(image)) {
				tbItem.setImage(image.split(",")[0]);
			}
			// 把商品添加到购物车列表
			cartList.add(tbItem);
		}
		// 写入cookie
		CookieUtils.setCookie(request, response, cookieCart, JSON.toJSONString(cartList), cookieCartExpire, true);
		// 返回添加购物车成功页面
		return "cartSuccess";
	}

	/**
	 * 展示购物车列表, 返回购物车列表视图
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/cart")
	public String showCartList(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 	1. 从cookie中获取购物车列表
		 *  2. 判断用户是否登录
		 *  	登录状态:
		 *  		1). 把客户端(cookie)中的购物车商品和服务端(redis)中的购物车商品合并
		 *  		2). 删除cookie中的购物车
		 *  		3). 从redis中获取购物车列表, 传递到页面, 展示
		 *  	未登录:	把cookie中列表传递到页面,展示
		 */

		// 从cookie中取购物车列表
		List<TbItem> cartList = this.getCartListFromCookie(request);

		// 判断用户是否为登录状态
		TbUser tbUser = (TbUser) request.getAttribute("user");
		if (tbUser != null) {
			// 合并
			Long userId = tbUser.getId();
			cartService.mergeCart(userId, cartList);
			// 删除cookie中的购物车
			CookieUtils.deleteCookie(request, response, cookieCart);
			// 从redis中获取购物车列表
			cartList = cartService.getCartList(userId);
		}

		// 把列表传递给页面
		request.setAttribute("cartList", cartList);
		// 返回逻辑视图
		return "cart";
	}

	/**
	 * 更新购物车商品商品数量
	 * 
	 * @param itemId
	 *            商品id
	 * @param num
	 *            商品修改后的数量
	 * @return
	 */
	@PostMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("user");
		if (tbUser != null) {
			cartService.updateCartNum(tbUser.getId(), itemId, num);
			return E3Result.ok();
		}

		// 从cookie取得购物车列表
		List<TbItem> cartList = this.getCartListFromCookie(request);
		// 遍历商品列表找到对应商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId() == itemId.longValue()) {
				// 更新数量
				tbItem.setNum(num);
				break;
			}
		}

		// 把购物车列表写回cookie
		CookieUtils.setCookie(request, response, cookieCart, JSON.toJSONString(cartList), cookieCartExpire, true);

		// 返回成功
		return E3Result.ok();

		// 解决请求*.html后缀无法返回json数据的问题, 在web.xml里添加一个url-pattern的拦截格式
	}

	/**
	 * 删除购物车商品
	 * 
	 * @return 购物车列表视图
	 */
	@GetMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 判断用户是否登录
		TbUser tbUser = (TbUser) request.getAttribute("user");
		if (tbUser != null) {
			cartService.deleteCartItem(tbUser.getId(), itemId);
			return "redirect:/cart/cart.html";
		}

		// 从cookie中取购物车列表
		List<TbItem> cartList = this.getCartListFromCookie(request);
		// 遍历列表,找到要删除的商品
		Iterator<TbItem> iterator = cartList.iterator();
		while (iterator.hasNext()) {
			TbItem tbItem = iterator.next();
			if (tbItem.getId() == itemId.longValue()) {
				// 删除商品
				iterator.remove();
				break;
			}
		}

		// 把购物车列表写入cookie
		CookieUtils.setCookie(request, response, cookieCart, JSON.toJSONString(cartList), cookieCartExpire, true);
		// 重定向到逻辑视图, 绝对路径 在项目后面拼接 /cart/cart.html
		return "redirect:/cart/cart.html";
	}

	/**
	 * 从cookie中取购物车列表
	 * 
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		// 购物车列表在cookie中存放的名字
		String cookieName = cookieCart;
		String jsonString = CookieUtils.getCookieValue(request, cookieName, true);

		if (StringUtils.isBlank(jsonString)) {
			// 购物车中商品为空
			// return Collections.emptyList(); // Collections.emptyList(); 空列表（不可变）。不能执行 add操作
			return new ArrayList<>();
		}

		// 把json转换成商品列表
		return JSON.parseArray(jsonString, TbItem.class);
	}

}
