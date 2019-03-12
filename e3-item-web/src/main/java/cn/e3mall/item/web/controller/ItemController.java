package cn.e3mall.item.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.common.base.entity.ItemDesc;
import cn.e3mall.item.web.core.BaseController;

/**
 * 商品详情管理
 *
 * @author colg
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController{

    @RequestMapping("/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        // 获取商品信息
        Item item = itemService.getTbItemById(itemId);
        // 获取商品描述
        ItemDesc itemDesc = itemDescService.findById(itemId);
        // 获取商品规格参数
        
        // 传递到页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "index";
    }
}
