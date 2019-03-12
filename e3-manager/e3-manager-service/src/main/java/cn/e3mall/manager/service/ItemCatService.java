package cn.e3mall.manager.service;

import java.util.List;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;

/**
 * 
 *
 * @author colg
 */
public interface ItemCatService {

    /**
     * 获取商品分类树形结构
     *
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getItemCatList(Long parentId);

    /**
     * 根据id查询商品分类
     *
     * @param id
     * @return
     */
    E3Result findItemCatById(Long id);

}
