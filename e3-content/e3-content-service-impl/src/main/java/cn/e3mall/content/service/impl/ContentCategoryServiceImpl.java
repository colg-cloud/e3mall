package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.e3mall.common.base.entity.ContentCategory;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.content.service.core.BaseServiceImpl;

/**
 * 
 *
 * @author colg
 */
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl implements ContentCategoryService {

    @Override
    public List<EasyUiTreeNode> getContentCategoryList(Long parentId) {
        return contentCategoryMapper.getContentCategoryList(parentId);
    }

    @Override
    public E3Result addContentCategory(Long parentId, String name) {
        Date now = new Date();
        
        // 添加内容分类
        ContentCategory contentCategory = new ContentCategory();
        // id为自增, 不需要设置
        contentCategory.setParentId(parentId)
                       .setName(name)
                       // 状态。可选值:1(正常),2(删除)
                       .setStatus(1)
                       // 默认排序: 1
                       .setSortOrder(1)
                       // 该类目是否为父类目，1为true，0为false; 新添加的节点为叶子节点
                       .setIsParent(false)
                       .setCreated(now)
                       .setUpdated(now);
        // 添加内容分类: 返回主键
        contentCategoryMapper.insertContentCategory(contentCategory);
        
        // 该类目是否为父类目，1为true，0为false; 如果不是父类目, 改为父类目
        ContentCategory dbContentCategory = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
        if (!dbContentCategory.getIsParent()) {
            dbContentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKeySelective(dbContentCategory);
        }
        
        return E3Result.ok(contentCategory);
    }

    @Override
    public E3Result updateContentCategory(Long id, String name) {
        ContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        contentCategory.setName(name)
                       .setUpdated(new Date());
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return E3Result.ok(contentCategory);
    }

    @Override
    public E3Result deleteContentCategory(Long id) {
        Date now = new Date();
        
        ContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        // 父节点不允许删除
        if (contentCategory.getIsParent()) {
            return E3Result.fail();
        }
        
        // 状态。可选值:1(正常),2(删除)
        contentCategory.setStatus(2)
                       .setUpdated(now);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        
        // 判断父节点是否还有其他子节点; 如果没有, 把isParent改为false
        Long parentId = contentCategory.getParentId();
        int count = contentCategoryMapper.selectCountByParentId(parentId);
        if (count == 0) {
            ContentCategory dbContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
            dbContentCategory.setIsParent(false)
                             .setUpdated(now);
            contentCategoryMapper.updateByPrimaryKeySelective(dbContentCategory);
        }
        return E3Result.ok();
    }

}
