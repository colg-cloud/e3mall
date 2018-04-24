package cn.e3mall.manager.dao;

import java.util.List;

import cn.e3mall.manager.pojo.TbOrderShipping;

/**
 * 
 *
 * @author colg
 */
public interface TbOrderShippingMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(TbOrderShipping record);

    TbOrderShipping selectByPrimaryKey(String orderId);

    List<TbOrderShipping> selectAll();

    int updateByPrimaryKey(TbOrderShipping record);
}