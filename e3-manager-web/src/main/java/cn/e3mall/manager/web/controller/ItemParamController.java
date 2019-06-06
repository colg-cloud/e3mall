package cn.e3mall.manager.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.manager.web.core.BaseController;

/**
 * 商品规格模版管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/manager/item/param")
public class ItemParamController extends BaseController {

    /**
     * 分页查询商品规格模版列表
     *
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    @PostMapping("/list")
    public EasyUIDataGridResult getList(Integer page, Integer rows) {
        return itemParamService.getItemParamList(page, rows);
    }

    /**
     * 根据商品类目id查询商品规格模版列表
     *
     * @param itemCatId 商品类目id
     * @return
     */
    @GetMapping("/select/{itemCatId}")
    public E3Result selectByItemCatId(@PathVariable Long itemCatId) {
        return itemParamService.findItemParamByItemCatId(itemCatId);
    }

    /**
     * 添加商品规格模版
     *
     * @param itemCatId
     * @param paramData
     * @return
     */
    @PostMapping("/save/{itemCatId}")
    public E3Result save(@PathVariable Long itemCatId, String paramData) {
        return itemParamService.addItemParam(itemCatId, paramData);
    }

    /**
     * 修改商品规格模版
     *
     * @param id
     * @param paramData
     * @return
     */
    @PostMapping("/update")
    public E3Result update(Long id, String paramData) {
        return itemParamService.updateItemParam(id, paramData);
    }

    /**
     * 批量删除商品规格模版
     *
     * @param ids 商品规格参数ids
     * @return
     */
    @PostMapping("/delete")
    public E3Result delete(String ids) {
        return itemParamService.deleteItemParam(ids);
    }

}
