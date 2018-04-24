package cn.e3mall.manager.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUiTreeNode;

/**
 * 
 *
 * @author colg
 */
public interface TbItemCatService {

	List<EasyUiTreeNode> getItemCatList(Long parentId);
	
}
