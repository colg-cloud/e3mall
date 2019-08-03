package cn.e3mall.manager.web.controller;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.manager.web.core.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * - 商品规格模版管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/item/param")
public class ItemParamController extends BaseController {

    /**
     * 分页查询商品规格模版列表
     *
     * @param page  页码
     * @param rows  每页记录数
     * @param sort  排序字段
     * @param order 排序方式
     * @return
     */
    @PostMapping("/list")
    public EasyUIDataGridResult getList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam String sort, @RequestParam String order) {
        return itemParamService.getItemParamList(page, rows, sort, order);
    }

    /**
     * 根据商品类目id查询商品规格模版列表
     *
     * @param itemCatId 商品类目id
     * @return
     */
    @PostMapping("/select/{itemCatId}")
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
    public E3Result update(@RequestParam Long id, String paramData) {
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
