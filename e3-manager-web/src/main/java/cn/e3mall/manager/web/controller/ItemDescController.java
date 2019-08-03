package cn.e3mall.manager.web.controller;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.manager.web.core.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * - 商品描述管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/item/desc")
public class ItemDescController extends BaseController {

    /**
     * 根据商品id获取商品详情
     *
     * @param itemId 商品id
     * @return
     */
    @PostMapping("/{id}")
    public E3Result findById(@PathVariable("id") Long itemId) {
        return E3Result.ok(itemDescService.findById(itemId));
    }

}
