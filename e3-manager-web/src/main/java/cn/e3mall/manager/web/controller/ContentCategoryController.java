package cn.e3mall.manager.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUiTreeNode;
import cn.e3mall.manager.web.core.BaseController;

/**
 * 内容分类管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/manager/content/category")
public class ContentCategoryController extends BaseController {

    /**
     * 获取内容分类树形结构
     *
     * @param parentId
     * @return
     */
    @GetMapping("/list")
    public List<EasyUiTreeNode> getContentList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategoryList(parentId);
    }
    
    /**
     * 添加内容分类节点
     *
     * @param parentId
     * @param name
     * @return
     */
    @PostMapping("/create")
    public E3Result createContentCategory(Long parentId, String name) {
        return contentCategoryService.addContentCategory(parentId, name);
    }
    
    /**
     * 修改内容分类节点
     *
     * @param id
     * @param name
     * @return
     */
    @PostMapping("/update")
    public E3Result updateContentCategory(Long id, String name) {
        return contentCategoryService.updateContentCategory(id, name);
    }
    
    /**
     * 删除内容分类节点
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public E3Result deleteContentCategory(Long id) {
        return contentCategoryService.deleteContentCategory(id);
    }
}
