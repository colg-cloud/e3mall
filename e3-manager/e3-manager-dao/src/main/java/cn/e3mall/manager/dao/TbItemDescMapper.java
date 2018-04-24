package cn.e3mall.manager.dao;

import java.util.List;

import cn.e3mall.manager.pojo.TbItemDesc;

/**
 * 
 *
 * @author colg
 */
public interface TbItemDescMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(TbItemDesc record);

    TbItemDesc selectByPrimaryKey(Long itemId);

    List<TbItemDesc> selectAll();

    int updateByPrimaryKey(TbItemDesc record);

}