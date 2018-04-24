package cn.e3mall.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.manager.core.BaseController;

/**
 * 内容分类管理Controller
 *
 * @author colg
 */
@RestController
@RequestMapping("/tb/content/category")
public class TbContentCategoryController extends BaseController {

	/**
	 * 内容分类tree
	 * 
	 * @param parentId
	 * @return
	 */
	@GetMapping("/list")
	public List<EasyUiTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		return tbContentCategoryService.getContentCategoryList(parentId);
	}

	/**
	 * 添加内容分类节点
	 * 
	 * @param parentId
	 * @param name
	 * @return
	 */
	@PostMapping("/create")
	public E3Result createContentCategory(Long parentId, String name) {
		return tbContentCategoryService.addContentCategory(parentId, name);
	}

	/**
	 * 修改内容分类节点
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@PostMapping("/update")
	public E3Result updateContentCategory(Long id, String name) {
		return tbContentCategoryService.updateContentCategory(id, name);
	}

	/**
	 * 删除内容分类节点
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/delete")
	public E3Result deleteContentCategory(Long id) {
		return tbContentCategoryService.deleteContentCategory(id);
	}
}