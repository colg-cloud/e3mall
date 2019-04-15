package cn.e3mall.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.e3mall.common.base.entity.ItemParam;
import cn.e3mall.common.base.vo.ItemParamVo;

/**
 * - @mbg.generated
 *
 * @author colg
 */
public interface ItemParamMapper extends tk.mybatis.mapper.common.Mapper<ItemParam> {

    /**
     * 查询商品规格模版列表
     *
     * @return
     */
    List<ItemParamVo> getItemParamList();

    /**
     * 根据商品类目id查询商品规格模版列表
     *
     * @param itemCatId
     * @return
     */
    ItemParam findItemParamByItemCatId(@Param("itemCatId") Long itemCatId);

    /**
     * 批量删除商品规格模版
     *
     * @param ids
     */
    void deleteItemParam(@Param("ids") List<String> ids);
}