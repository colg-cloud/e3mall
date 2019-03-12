package cn.e3mall.manager.service;

import cn.e3mall.common.base.entity.ItemDesc;

/**
 * 
 *
 * @author colg
 */
public interface ItemDescService {

    /**
     * 根据商品id获取商品详情
     *
     * @param itemId
     * @return
     */
    ItemDesc findById(Long itemId);

}
