package cn.e3mall.manager.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;
import cn.e3mall.manager.web.core.BaseController;

/**
 * 商品分类管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/manager/item/cat")
public class ItemCatController extends BaseController {

	/**
	 * 获取商品分类树形结构
	 *
	 * @param parentId
	 * @return
	 */
	@PostMapping("/list")
	public List<EasyUiTreeNode> list(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		// 调用服务查询节点列表
		return itemCatService.getItemCatList(parentId);
	}
	
	/**
	 * 根据id查询商品分类
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public E3Result find(@PathVariable Long id) {
	    return itemCatService.findItemCatById(id);
	}
}