package cn.e3mall.manager.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;
import cn.e3mall.manager.service.ItemCatService;
import cn.e3mall.manager.service.core.BaseServiceImpl;

/**
 * 
 *
 * @author colg
 */
@Service
public class ItemCatServiceImpl extends BaseServiceImpl implements ItemCatService {

    @Override
    public List<EasyUiTreeNode> getItemCatList(Long parentId) {
        // 通过Sql直接把结果集封装到List<EasyUiTreeNode>
        return itemCatMapper.selectEasyUITreeNodeByParentId(parentId);
    }

    @Override
    public E3Result findItemCatById(Long id) {
        return E3Result.ok(itemCatMapper.selectByPrimaryKey(id));
    }

}
