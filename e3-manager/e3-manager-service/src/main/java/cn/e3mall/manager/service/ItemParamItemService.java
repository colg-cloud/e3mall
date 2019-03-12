package cn.e3mall.manager.service;

import cn.e3mall.common.base.pojo.E3Result;

/**
 * 
 *
 * @author colg
 */
public interface ItemParamItemService {

    /**
     * 根据商品id查询商品规格参数
     *
     * @param itemId
     * @return
     */
    E3Result selectItemParamItemByItemId(Long itemId);

}
