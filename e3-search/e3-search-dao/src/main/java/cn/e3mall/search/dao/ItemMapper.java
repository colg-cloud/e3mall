package cn.e3mall.search.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.common.base.entity.Item;
import cn.e3mall.common.solr.ItemSearch;

/**
 * - @mbg.generated
 *
 * @author colg
 */
public interface ItemMapper extends tk.mybatis.mapper.common.Mapper<Item> {

    /**
     * 查询商品列表
     *
     * @return
     */
    List<ItemSearch> selectItemSearchList();

    /**
     * 根据商品id查询商品信息
     *
     * @param id
     * @return
     */
    ItemSearch findItemSearchById(@Param("id") Long id);
}