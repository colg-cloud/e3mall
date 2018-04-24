package cn.e3mall.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.manager.core.BaseController;

/**
 * 商品分类管理Contoller
 *
 * @author colg
 */
@RestController
@RequestMapping("/tb/item/cat")
public class TbItemCatController extends BaseController {

	/**
	 * 商品分类tree
	 * 
	 * @param parentId
	 * @return
	 */
	@PostMapping("/list")
	public List<EasyUiTreeNode> list(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		// 调用服务查询节点列表
		return tbItemCatService.getItemCatList(parentId);
	}
}