package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.manager.pojo.TbContent;

/**
 * 
 *
 * @author colg
 */
public interface TbContentService {

	/**
	 * 根据内容类目ID分页查询内容列表
	 * 
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult queryListByCategoryId(Long categoryId, Integer page, Integer rows);

	/**
	 * 新增内容
	 * 
	 * @param tbContent
	 * @return
	 */
	E3Result saveContent(TbContent tbContent);

	/**
	 * 修改内容
	 * 
	 * @param id
	 * @param tbContent
	 * @return
	 */
	E3Result editContent(Long id, TbContent tbContent);

	/**
	 * 批量删除内容
	 * 
	 * @param ids
	 * @return
	 */
	E3Result deleteContent(String ids);

	/**
	 * 根据内容类目ID查询内容列表
	 * 
	 * @param categoryId
	 * @return
	 */
	List<TbContent> getContentListByCategoryId(Long categoryId);
}
