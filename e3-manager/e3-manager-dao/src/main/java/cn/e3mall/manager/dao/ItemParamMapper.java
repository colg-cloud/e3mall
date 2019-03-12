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

    List<ItemParamVo> getItemParamList();

    ItemParam findItemParamByItemCatId(@Param("itemCatId") Long itemCatId);

    void deleteItemParam(@Param("ids") List<String> ids);
}