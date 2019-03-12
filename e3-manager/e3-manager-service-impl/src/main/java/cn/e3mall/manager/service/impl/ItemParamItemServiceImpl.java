package cn.e3mall.manager.service.impl;

import org.springframework.stereotype.Service;

import cn.e3mall.common.base.pojo.E3Result;
import cn.e3mall.manager.service.ItemParamItemService;
import cn.e3mall.manager.service.core.BaseServiceImpl;

/**
 * 
 *
 * @author colg
 */
@Service
public class ItemParamItemServiceImpl extends BaseServiceImpl implements ItemParamItemService {

    @Override
    public E3Result selectItemParamItemByItemId(Long itemId) {
        return E3Result.ok(itemParamItemMapper.findItemParamItemByItemId(itemId));
    }
}
