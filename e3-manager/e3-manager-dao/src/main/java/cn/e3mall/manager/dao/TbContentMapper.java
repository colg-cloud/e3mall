package cn.e3mall.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.manager.pojo.TbContent;

/**
 * 内容Mapper
 *
 * @author colg
 */
public interface TbContentMapper {
	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 添加对象
	 * 
	 * @param record
	 * @return
	 */
	int insert(TbContent record);

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	TbContent selectByPrimaryKey(Long id);

	/**
	 * 获取所有内容列表
	 * 
	 * @return
	 */
	List<TbContent> selectAll();

	/**
	 * 修改内容
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(TbContent record);

	/**
	 * 根据类目id获取内容列表
	 * 
	 * @param categoryId
	 * @return
	 */
	List<TbContent> queryListByCategoryId(Long categoryId);

	/**
	 * 根据内容id批量删除
	 * 
	 * @param contentIds
	 */
	void deleteByIds(@Param("contentIds") String[] contentIds);

}