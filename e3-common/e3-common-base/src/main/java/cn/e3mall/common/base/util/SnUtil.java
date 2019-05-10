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
     * @return 格式: yyyy-MM-dd_0SSS
     */
    public static String genImageName() {
        return DateUtil.format(new Date(), DatePattern.NORM_DATE_FORMAT) + "_" + String.format("%04d", DateUtil.thisMillsecond());
    }

    /**
     * 商品id生成
     *
     * @return 格式: yyyyMMdd0SSS
     */
    public static long genItemId() {
        String string = DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT) + String.format("%04d", DateUtil.thisMillsecond());
        return Long.parseLong(string);
    }

}
