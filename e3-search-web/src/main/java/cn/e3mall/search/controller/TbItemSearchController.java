package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.vo.SearchResult;
import cn.e3mall.search.core.BaseController;

/**
 * 商品搜索Controller
 *
 * @author colg
 */
@Controller
public class TbItemSearchController extends BaseController {

	/** 搜索结果，每页显示的记录数 */
	@Value("${SEARCH_RESULT_ROWS}")
	private Integer searchResultRows;

	/**
	 * 搜索
	 * 
	 * @param keyword
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public String searchItemList(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) {
		// get请求，乱码，更改tomcat编码，或者转码
//		keyword = StringUtil.iso2Utf(keyword);

		// 查询商品列表
		SearchResult searchResult = tbItemService.search(keyword, page, searchResultRows);

		// 把结果传递给页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResult.getRecourdCount());
		model.addAttribute("itemList", searchResult.getItemSerachs());

//		System.out.println(1/0);
		// 返回逻辑视图
		return "search";
	}
}
