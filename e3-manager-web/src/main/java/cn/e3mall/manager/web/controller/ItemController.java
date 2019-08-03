package cn.e3mall.manager.web.controller;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.common.base.pojo.PicResult;
import cn.e3mall.common.fastdfs.FastDfsClient;
import cn.e3mall.manager.web.core.BaseController;
import cn.hutool.core.io.FileUtil;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 商品管理
 *
 * @author colg
 */
@RestController
@RequestMapping("/item")
public class ItemController extends BaseController {

    @Value("${trackerServers}")
    private String trackerServers;
    @Value("${imageServer}")
    private String imageServer;

    /**
     * 分页查询商品列表
     *
     * @param page  页码
     * @param rows  每页记录数
     * @param sort  排序字段
     * @param order 排序方式
     * @return
     */
    @PostMapping("/list")
    public EasyUIDataGridResult selectItemList(@RequestParam Integer page, @RequestParam Integer rows, @RequestParam String sort, @RequestParam String order) {
        // 调用服务查询商品列表
        return itemService.selectItemList(page, rows, sort, order);
    }

    /**
     * 上传图片
     *
     * @param multipartFile 图片
     * @return
     */
    @PostMapping("/pic/upload")
    public PicResult uploadFile(@RequestParam("uploadFile") MultipartFile multipartFile) {
        // 把图片上传到图片服务器
        FastDfsClient fastDfsClient;
        String url;
        try {
            fastDfsClient = FastDfsClient.create(trackerServers);
            fastDfsClient.setImageServer(imageServer);
            // 完整的url: http://ip/group/filename
            url = fastDfsClient.uploadFile(multipartFile.getBytes(), FileUtil.extName(multipartFile.getOriginalFilename()));
        } catch (IOException | MyException e1) {
            e1.printStackTrace();
            return PicResult.fail("图片上传失败!");
        }
        return PicResult.ok(url);
    }

    /**
     * 添加商品
     *
     * @param item       商品信息
     * @param desc       商品描述
     * @param itemParams 商品规格参数
     * @return
     */
    @PostMapping("/save")
    public E3Result save(Item item, String desc, String itemParams) {
        return itemService.addItem(item, desc, itemParams);
    }

    /**
     * 修改商品
     *
     * @param id          商品id
     * @param item        商品信息
     * @param desc        商品描述
     * @param itemParamId 商品规格id
     * @param itemParams  商品规格参数
     * @return
     */
    @PostMapping("/update")
    public E3Result update(@RequestParam Long id, Item item, String desc, Long itemParamId, String itemParams) {
        return itemService.updateItem(id, item, desc, itemParamId, itemParams);
    }

    /**
     * 批量删除商品
     *
     * @param ids 商品ids
     * @return
     */
    @PostMapping("/delete")
    public E3Result delete(String ids) {
        return itemService.delete(ids);
    }

    /**
     * 批量下架商品
     *
     * @param ids 商品ids
     * @return
     */
    @PostMapping("/inStock")
    public E3Result inStock(String ids) {
        return itemService.updateInStock(ids);
    }

    /**
     * 批量上架商品
     *
     * @param ids 商品ids
     * @return
     */
    @PostMapping("/reShelf")
    public E3Result reShelf(String ids) {
        return itemService.updateReShelf(ids);
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