package cn.e3mall.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.manager.pojo.TbItem;

/**
 * 
 *
 * @author colg
 */
public interface TbItemMapper {
	
	int deleteByPrimaryKey(Long id);

	int insert(TbItem record);

	TbItem selectByPrimaryKey(Long id);

	List<TbItem> selectAll();

	int updateByPrimaryKey(TbItem record);

	void updateRemove(@Param("itemIds") String[] itemIds);

	void updateInstock(@Param("itemIds") String[] itemIds);

	void updateReshelf(@Param("itemIds") String[] itemIds);

}