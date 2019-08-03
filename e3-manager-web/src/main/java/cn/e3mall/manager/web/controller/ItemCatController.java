package cn.e3mall.manager.web.controller;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUiTreeNodeResult;
import cn.e3mall.manager.web.core.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * - 商品分类管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/item/cat")
public class ItemCatController extends BaseController {

    /**
     * 获取商品分类树形结构
     *
     * @param parentId 父级节点
     * @return
     */
    @PostMapping("/list")
    public List<EasyUiTreeNodeResult> list(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        // 调用服务查询节点列表
        return itemCatService.getItemCatList(parentId);
    }

    /**
     * 根据id查询商品分类
     *
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public E3Result find(@PathVariable Long id) {
        return itemCatService.findItemCatById(id);
    }

}