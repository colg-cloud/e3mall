package cn.e3mall.manager.dao;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.common.base.entity.ItemParamItem;

/**
 * - @mbg.generated
 *
 * @author colg
 */
public interface ItemParamItemMapper extends tk.mybatis.mapper.common.Mapper<ItemParamItem> {

    /**
     * 根据商品id查询商品规格参数
     *
     * @param itemId
     * @return
     */
    ItemParamItem findItemParamItemByItemId(@Param("itemId") Long itemId);
}