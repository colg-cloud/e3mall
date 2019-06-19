package cn.e3mall.common.base.util;

import java.util.Date;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 序号生成工具
 *
 * @author colg
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnUtil {

    /**
     * 图片名称生成
     *
     * @return 格式: yyyy-MM-dd_0SSS
     */
    public static String genImageName() {
        return DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + "_" + String.format("%04d", DateUtil.thisMillsecond());
    }

    /**
     * 商品id生成
     *
     * @return 格式: yyyyMMdd0SSS
     */
    public static long genItemId() {
        return Long.parseLong(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + String.format("%04d", DateUtil.thisMillsecond()));
    }

}
