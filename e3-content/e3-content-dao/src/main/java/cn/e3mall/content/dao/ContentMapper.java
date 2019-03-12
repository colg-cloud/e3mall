package cn.e3mall.content.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.common.base.entity.Content;

/**
 * - @mbg.generated
 *
 * @author colg
 */
public interface ContentMapper extends tk.mybatis.mapper.common.Mapper<Content> {

    /**
     * 根据内容类目id查询内容列表
     *
     * @param categoryId
     * @return
     */
    List<Content> queryListByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据内容类目id分页查询内容列表
     *
     * @param categoryId
     * @return
     */
    List<Content> selectContentListByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 批量删除内容
     *
     * @param categoryId
     * @param ids
     */
    void deleteContentByIds(@Param("categoryId") Long categoryId, @Param("ids") List<String> ids);

}