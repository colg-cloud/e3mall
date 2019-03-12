package cn.e3mall.manager.web.controller;

import java.io.IOException;

import org.csource.common.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.common.base.pojo.PicResult;
import cn.e3mall.common.fastdfs.FastDfsClient;
import cn.e3mall.manager.web.core.BaseController;
import cn.hutool.core.io.FileUtil;

/**
 * 商品管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/manager/item")
public class ItemController extends BaseController {
    
    /**
     * 分页查询商品列表
     *
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/list")
    public EasyUIDataGridResult selectItemList(Integer page, Integer rows) {
        // 调用服务查询商品列表
        return itemService.selectItemList(page, rows);
    }

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     * @throws MyException 
     * @throws IOException 
     */
    @PostMapping("/pic/upload")
    public PicResult uploadFile(@RequestParam("uploadFile") MultipartFile multipartFile) {
        // 把图片上传到图片服务器
        FastDfsClient fastDfsClient;
        String url;
        try {
            fastDfsClient = FastDfsClient.create();
            url = fastDfsClient.uploadFile(multipartFile.getBytes(), FileUtil.extName(multipartFile.getOriginalFilename()));
        } catch (IOException | MyException e1) {
            e1.printStackTrace();
            return PicResult.fail("图片上传失败!");
        }
        
        // 完整的url: http://ip/group/filename
        return PicResult.ok(url);
    }

    /**
     * 添加商品
     *
     * @param item
     * @param desc
     * @param itemParams
     * @return
     */
    @PostMapping("/save")
    public E3Result save(Item item, String desc, String itemParams) {
        return itemService.addItem(item, desc, itemParams);
    }

    /**
     * 修改商品
     *
     * @param id 商品id
     * @param item 商品信息
     * @param desc 商品描述
     * @param itemParamId 商品规格id
     * @param itemParams 商品规格参数
     * @return
     */
    @PostMapping("/update")
    public E3Result update(Long id, Item item, String desc, Long itemParamId, String itemParams) {
        return itemService.updateItem(id, item, desc, itemParamId, itemParams);
    }
    
    /**
     * 批量删除商品
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public E3Result delete(String ids) {
        return itemService.delete(ids);
    }
    
    /**
     * 批量下架商品
     *
     * @param ids
     * @return
     */
    @PostMapping("/instock")
    public E3Result instock(String ids) {
        return itemService.updateInstock(ids);
    }
    
    /**
     * 批量上架商品
     *
     * @param ids
     * @return
     */
    @PostMapping("/reshelf")
    public E3Result reshelf(String ids) {
        return itemService.updateReshelf(ids);
    }
    
    /**
     * 一键导入所有商品数据到Solr索引库
     *
     * @return
     */
    @PostMapping("/import")
    public E3Result importAllItem() {
        return itemSearchService.importAllItem();
    }

}