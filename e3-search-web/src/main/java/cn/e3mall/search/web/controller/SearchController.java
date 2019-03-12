package cn.e3mall.search.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.solr.SearchResult;
import cn.e3mall.search.web.core.BaseController;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 搜索系统
 *
 * @author colg
 */
@Slf4j
@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {
    
    /** 搜索结果, 每页显示的记录数 */
    @Value("${search_result_rows}")
    private Integer searchResultRows;

    /**
     * 页面跳转
     *
     * @param view 页面地址
     * @param keyword 关键字
     * @param page 页码(默认第一页)
     * @param model 每页记录数
     * @return
     */
    @GetMapping("/{view}")
    public String showView(@PathVariable String view, String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) {
        // 查询商品列表
        SearchResult searchResult = itemSearchService.search(keyword, page, searchResultRows);
        
        // 把结果传递到页面
        model.addAttribute("query", keyword);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("recourdCount", searchResult.getRecourdCount());
        model.addAttribute("itemList", searchResult.getItemSearchs());
        
        // 异常测试
        if (RandomUtil.randomInt(11) == 10) {
            log.info("异常测试: {}", 1 / 0);
        }
        
        return view;
    }
    
}
