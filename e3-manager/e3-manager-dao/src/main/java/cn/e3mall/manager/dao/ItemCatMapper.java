package cn.e3mall.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.common.base.entity.ItemCat;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;

/**
 * - @mbg.generated
 *
 * @author colg
 */
public interface ItemCatMapper extends tk.mybatis.mapper.common.Mapper<ItemCat> {

    /**
     * 获取商品分类树形结构
     *
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> selectEasyUITreeNodeByParentId(@Param("parentId") Long parentId);
}