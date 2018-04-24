package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.content.core.BaseServiceImpl;
import cn.e3mall.content.service.TbContentCategoryService;
import cn.e3mall.manager.pojo.TbContentCategory;

/**
 * 
 *
 * @author colg
 */
@Service
public class TbContentCategoryServiceImpl extends BaseServiceImpl implements TbContentCategoryService {

	@Override
	public List<EasyUiTreeNode> getContentCategoryList(Long parentId) {
		return tbContentCategoryMapper.getContentCategoryList(parentId);
	}

	@Override
	public E3Result addContentCategory(Long parentId, String name) {
		// 设置属性
		TbContentCategory tbContentCategory = new TbContentCategory();
		// id为自增，不需要设置
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		// 状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);
		// 默认排序 1
		tbContentCategory.setSortOrder(1);
		// 该类目是否为父类目，1为true，0为false，新添加的节点为叶子节点
		tbContentCategory.setIsParent(false);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		// 插入到数据库
		tbContentCategoryMapper.insert(tbContentCategory);

		// 判断父节点的isParent，如果不是true改为true
		// 根据parentId查询父节点
		TbContentCategory dbTbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!dbTbContentCategory.getIsParent()) {
			dbTbContentCategory.setIsParent(true);
			// 更新到数据库
			tbContentCategoryMapper.updateByPrimaryKey(dbTbContentCategory);
		}
		// 返回E3Reqult，包含pojo
		return E3Result.ok(tbContentCategory);
	}

	@Override
	public E3Result updateContentCategory(Long id, String name) {
		TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		tbContentCategory.setName(name);
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCategory(Long id) {
		TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		if (tbContentCategory.getIsParent()) {
			return E3Result.fail();
		}
		// 状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(2);
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);

		// 判断父节点是否还有其他子节点，如果没有，把isParent该为false
		Long parentId = tbContentCategory.getParentId();
		List<TbContentCategory> list = tbContentCategoryMapper.selectByParentId(parentId);
		if (list == null || list.isEmpty()) {
			TbContentCategory dbTbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
			dbTbContentCategory.setIsParent(false);
			dbTbContentCategory.setUpdated(new Date());
			tbContentCategoryMapper.updateByPrimaryKey(dbTbContentCategory);
		}
		return E3Result.ok();
	}

}