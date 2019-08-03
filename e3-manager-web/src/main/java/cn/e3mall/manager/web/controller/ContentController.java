package cn.e3mall.manager.web.controller;

import cn.e3mall.common.base.entity.Content;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.manager.web.core.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * - 内容管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/content")
public class ContentController extends BaseController {

    /**
     * 根据内容类目id分页查询内容列表
     *
     * @param page       页码
     * @param rows       每页记录数
     * @param sort       排序字段
     * @param order      排序方式
     * @param categoryId 内容类目id
     * @return
     */
    @PostMapping("/query/list")
    public EasyUIDataGridResult queryListByCategoryId(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam String sort, @RequestParam String order, @RequestParam Long categoryId) {
        return contentService.queryListByCategoryId(page, rows, sort, order, categoryId);
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
