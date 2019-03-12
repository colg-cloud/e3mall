package cn.e3mall.manager.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.e3mall.common.base.entity.ItemParam;
import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.common.base.pojo.EasyUIDataGridResult;
import cn.e3mall.manager.service.ItemParamService;
import cn.e3mall.manager.service.core.BaseServiceImpl;
import cn.hutool.core.util.StrUtil;

/**
 * 
 *
 * @author colg
 */
@Service
public class ItemParamServiceImpl extends BaseServiceImpl implements ItemParamService {

    @Override
    public EasyUIDataGridResult getItemParamList(Integer page, Integer rows) {
        return EasyUIDataGridResult.ok(PageHelper.startPage(page, rows).doSelectPage(() -> itemParamMapper.getItemParamList()));
    }

    @Override
    public E3Result findItemParamByItemCatId(Long itemCatId) {
        return E3Result.ok(itemParamMapper.findItemParamByItemCatId(itemCatId));
    }

    @Override
    public E3Result addItemParam(Long itemCatId, String paramData) {
        Date now = new Date();
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(itemCatId)
                 .setParamData(paramData)
                 .setCreated(now)
                 .setUpdated(now);
        itemParamMapper.insertSelective(itemParam);
        return E3Result.ok(itemParam);
    }

    @Override
    public E3Result updateItemParam(Long id, String paramData) {
        ItemParam itemParam = itemParamMapper.selectByPrimaryKey(id);
        itemParam.setParamData(paramData)
                 .setUpdated(new Date());
        itemParamMapper.updateByPrimaryKeySelective(itemParam);
        return E3Result.ok(itemParam);
    }

    @Override
    public E3Result deleteItemParam(String ids) {
        itemParamMapper.deleteItemParam(StrUtil.split(ids, ','));
        return E3Result.ok();
    }

}
