package cn.e3mall.manager.service;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;

/**
 * 
 *
 * @author colg
 */
public interface ItemParamService {

    /**
     * 分页查询商品规格模版列表
     *
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getItemParamList(Integer page, Integer rows);

    /**
     * 根据商品类目id查询商品规格模版列表
     *
     * @param itemCatId
     * @return
     */
    E3Result findItemParamByItemCatId(Long itemCatId);

    /**
     * 添加商品规格模版
     *
     * @param itemCatId
     * @param paramData
     * @return
     */
    E3Result addItemParam(Long itemCatId, String paramData);

    /**
     * 修改商品规格模版
     *
     * @param id
     * @param paramData
     * @return
     */
    E3Result updateItemParam(Long id, String paramData);

    /**
     * 批量删除商品规格模版
     *
     * @param ids
     * @return
     */
    E3Result deleteItemParam(String ids);

}
