package cn.e3mall.manager.dao;

import java.util.List;

import cn.e3mall.manager.pojo.TbItemParamItem;

/**
 * 
 *
 * @author colg
 */
public interface TbItemParamItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbItemParamItem record);

    TbItemParamItem selectByPrimaryKey(Long id);

    List<TbItemParamItem> selectAll();

    int updateByPrimaryKey(TbItemParamItem record);
}