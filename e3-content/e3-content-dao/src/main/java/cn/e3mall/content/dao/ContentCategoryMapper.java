package cn.e3mall.content.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.common.base.entity.ContentCategory;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;

/**
 * - @mbg.generated
 *
 * @author colg
 */
public interface ContentCategoryMapper extends tk.mybatis.mapper.common.Mapper<ContentCategory> {

    /**
     * 获取内容分类树形结构
     *
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getContentCategoryList(@Param("parentId") Long parentId);

    /**
     * 根据父id查询内容分类数量
     *
     * @param parentId
     * @return
     */
    int selectCountByParentId(@Param("parentId") Long parentId);

    /**
     * 添加内容分类
     *
     * @param contentCategory
     */
    void insertContentCategory(ContentCategory contentCategory);

}