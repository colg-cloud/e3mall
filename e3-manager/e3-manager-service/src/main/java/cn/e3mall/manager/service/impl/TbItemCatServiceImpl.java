package cn.e3mall.manager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.manager.core.BaseServiceImpl;
import cn.e3mall.manager.service.TbItemCatService;

/**
 * 
 *
 * @author colg
 */
@Service
public class TbItemCatServiceImpl extends BaseServiceImpl implements TbItemCatService {

	@Override
	public List<EasyUiTreeNode> getItemCatList(Long parentId) {
		// 通过Sql直接把结果封装到 List<EasyUITreeNode>
		return tbItemCatMapper.selectEasyUITreeNodeByParentId(parentId);
	}

}
