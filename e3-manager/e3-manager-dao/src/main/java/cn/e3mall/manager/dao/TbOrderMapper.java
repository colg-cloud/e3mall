package cn.e3mall.manager.dao;

import java.util.List;

import cn.e3mall.manager.pojo.TbOrder;

/**
 * 
 *
 * @author colg
 */
public interface TbOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(TbOrder record);

    TbOrder selectByPrimaryKey(String orderId);

    List<TbOrder> selectAll();

    int updateByPrimaryKey(TbOrder record);
}