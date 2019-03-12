package cn.e3mall.manager.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.manager.web.core.BaseController;

/**
 * 商品规格参数管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/manager/item/param/item")
public class ItemParamItemController extends BaseController {

    /**
     * 根据商品id查询商品规格参数
     *
     * @param itemId
     * @return
     */
    @GetMapping("/select/{itemId}")
    public E3Result selectByItemId(@PathVariable Long itemId) {
        return itemParamItemService.selectItemParamItemByItemId(itemId);
    }
}
