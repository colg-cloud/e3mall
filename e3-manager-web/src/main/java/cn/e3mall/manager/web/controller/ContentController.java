package cn.e3mall.manager.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.base.entity.Content;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.manager.web.core.BaseController;

/**
 * 内容管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/manager/content")
public class ContentController extends BaseController {

    /**
     * 根据内容类目id分页查询内容列表
     *
     * @param page       页码
     * @param rows       每页记录数
     * @param categoryId 内容类目id
     * @return
     */
    @PostMapping("/query/list")
    public EasyUIDataGridResult queryListByCategoryId(Integer page, Integer rows, Long categoryId) {
        return contentService.queryListByCategoryId(page, rows, categoryId);
    }

    /**
     * 新增内容
     *
     * @param content
     * @return
     */
    @PostMapping("/save")
    public E3Result saveContent(Content content) {
        return contentService.saveContent(content);
    }

    /**
     * 修改内容
     *
     * @param id
     * @param content
     * @return
     */
    @PostMapping("/edit")
    public E3Result editContent(Long id, Content content) {
        return contentService.editContent(id, content);
    }

    /**
     * 批量删除内容
     *
     * @param categoryId 内容类目id
     * @param ids        内容ids
     * @return
     */
    @PostMapping("/delete")
    public E3Result deleteContent(Long categoryId, String ids) {
        return contentService.deleteContent(categoryId, ids);
    }
}
