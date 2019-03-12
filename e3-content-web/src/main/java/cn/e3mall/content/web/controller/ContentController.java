package cn.e3mall.content.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.common.base.entity.Content;
import cn.e3mall.content.web.core.BaseController;

/**
 * 商城门户系统
 *
 * @author colg
 */
@Controller
@RequestMapping("/content")
public class ContentController extends BaseController {
    
    /** 大广告 */
    @Value("${content_carousel_id}")
    private Long contentCarouselId;

    /** 小广告 */
    @Value("${content_small_id}")
    private Long contentSmallId;

    /** 搜索框 */
    @Value("${content_search_id}")
    private Long contentSearchId;

    /**
     * 页面跳转
     *
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/{view}")
    public String showView(@PathVariable String view, Model model) {
        // 查询大广告 : 89
        List<Content> ad1List = contentService.getContentListByCategoryId(contentCarouselId);
        
        // 查询小广告 : 90
        List<Content> ad2List = contentService.getContentListByCategoryId(contentSmallId);
        
        // 查询搜索框下面广告 : 101
        List<Content> ad3List = contentService.getContentListByCategoryId(contentSearchId);
        
        model.addAttribute("ad1List", ad1List);
        model.addAttribute("ad2List", ad2List);
        model.addAttribute("ad3List", ad3List);
        
        return view;
    }
}
