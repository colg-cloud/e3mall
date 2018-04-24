package cn.e3mall.manager.dao;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.manager.pojo.TbContentCategory;

import java.util.List;

/**
 * 内容类目Mapper
 *
 * @author colg
 */
public interface TbContentCategoryMapper {
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
	int insert(TbContentCategory record);

	/**
	 * 根据id查找类目
	 * 
	 * @param id
	 * @return
	 */
	TbContentCategory selectByPrimaryKey(Long id);

	/**
	 * 获取所有类目
	 * 
	 * @return
	 */
	List<TbContentCategory> selectAll();

	/**
	 * 修改对象
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(TbContentCategory record);

	/**
	 * 根据父id查询treeNode
	 * 
	 * @param parentId
	 * @return
	 */
	List<EasyUiTreeNode> getContentCategoryList(Long parentId);

	/**
	 * 根据父id查询类目列表
	 * 
	 * @param parentId
	 * @return
	 */
	List<TbContentCategory> selectByParentId(Long parentId);
}