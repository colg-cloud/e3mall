package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;

/**
 * 
 *
 * @author colg
 */
public interface ContentCategoryService {

    /**
     * 获取内容分类树形结构
     *
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getContentCategoryList(Long parentId);

    /**
     * 添加内容分类节点
     *
     * @param parentId
     * @param name
     * @return
     */
    E3Result addContentCategory(Long parentId, String name);

    /**
     * 修改内容分类节点
     *
     * @param id
     * @param name
     * @return
     */
    E3Result updateContentCategory(Long id, String name);

    /**
     * 删除内容分类节点
     *
     * @param id
     * @return
     */
    E3Result deleteContentCategory(Long id);

}
