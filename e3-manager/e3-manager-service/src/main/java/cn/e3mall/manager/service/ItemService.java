package cn.e3mall.manager.service;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;

/**
 * @author colg
 */
public interface ItemService {

    /**
     * 根据商品id获取商品
     *
     * @param id
     * @return
     */
    Item getTbItemById(Long id);

    /**
     * 分页查询商品列表
     *
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    EasyUIDataGridResult selectItemList(Integer page, Integer rows);

    /**
     * 添加商品
     *
     * @param item      商品信息
     * @param desc      商品详情
     * @param paramData 商品规格参数
     * @return
     */
    E3Result addItem(Item item, String desc, String paramData);

    /**
     * 修改商品
     *
     * @param itemId      商品id
     * @param item        商品信息
     * @param desc        商品详情
     * @param itemParamId 商品规格id
     * @param paramData   商品规格参数
     * @return
     */
    E3Result updateItem(Long itemId, Item item, String desc, Long itemParamId, String paramData);

    /**
     * 批量删除商品
     *
     * @param ids 商品ids
     * @return
     */
    E3Result delete(String ids);

    /**
     * 批量下架商品
     *
     * @param ids 商品ids
     * @return
     */
    E3Result updateInstock(String ids);

    /**
     * 批量上架商品
     *
     * @param ids 商品ids
     * @return
     */
    E3Result updateReshelf(String ids);

}
