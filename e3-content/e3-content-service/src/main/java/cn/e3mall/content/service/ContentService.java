package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.base.entity.Content;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;

/**
 * 
 *
 * @author colg
 */
public interface ContentService {

    /**
     * 根据内容类目id查询内容列表
     *
     * @param categoryId
     * @return
     */
    List<Content> getContentListByCategoryId(Long categoryId);

    /**
     * 根据内容类目id分页查询内容列表
     *
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    EasyUIDataGridResult queryListByCategoryId(Integer page, Integer rows, Long categoryId);

    /**
     * 新增内容
     *
     * @param content
     * @return
     */
    E3Result saveContent(Content content);

    /**
     * 修改内容
     *
     * @param id
     * @param content
     * @return
     */
    E3Result editContent(Long id, Content content);

    /**
     * 批量删除内容
     *
     * @param categoryId
     * @param ids
     * @return
     */
    E3Result deleteContent(Long categoryId, String ids);

}
