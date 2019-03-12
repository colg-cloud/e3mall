package cn.e3mall.manager.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.manager.web.core.BaseController;

/**
 * 商品描述管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/manager/item/desc")
public class ItemDescController extends BaseController{

    /**
     * 根据商品id获取商品详情
     *
     * @param itemId
     * @return
     */
    @GetMapping("/{id}")
    public E3Result findById(@PathVariable("id") Long itemId) {
        return E3Result.ok(itemDescService.findById(itemId));
    }
}
