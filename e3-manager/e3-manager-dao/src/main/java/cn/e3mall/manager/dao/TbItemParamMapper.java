package cn.e3mall.manager.dao;

import java.util.List;

import cn.e3mall.manager.pojo.TbItemParam;

/**
 * 
 *
 * @author colg
 */
public interface TbItemParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbItemParam record);

    TbItemParam selectByPrimaryKey(Long id);

    List<TbItemParam> selectAll();

    int updateByPrimaryKey(TbItemParam record);
}