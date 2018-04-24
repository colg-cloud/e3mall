package cn.e3mall.manager.dao;

import java.util.List;

import cn.e3mall.manager.pojo.TbOrderItem;

/**
 * 
 *
 * @author colg
 */
public interface TbOrderItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(TbOrderItem record);

    TbOrderItem selectByPrimaryKey(String id);

    List<TbOrderItem> selectAll();

    int updateByPrimaryKey(TbOrderItem record);
}