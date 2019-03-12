package cn.e3mall.common.base.util;

import java.util.Date;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

/**
 * 序号生成工具
 *
 * @author colg
 */
public class SnUtil {

    /**
     * 图片名称生成
     *
     * @return
     */
    public static String genImageName() {
        // 格式: yyyy-MM-dd_0SSS
        return DateUtil.format(new Date(), DatePattern.NORM_DATE_FORMAT) + "_" + String.format("%04d", DateUtil.thisMillsecond());
    }

    /**
     * 商品id生成
     *
     * @return
     */
    public static long genItemId() {
        // 格式: yyyyMMdd0SSS
        String string = DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT) + String.format("%04d", DateUtil.thisMillsecond());
        return Long.parseLong(string);
    }
}
