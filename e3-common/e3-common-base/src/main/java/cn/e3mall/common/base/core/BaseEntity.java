package cn.e3mall.common.base.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * entity的基类, 建议所有实体类都继承
 *
 * @author colg
 */
public abstract class BaseEntity {

    @Override
    public String toString() {
        return JSON.toJSONString(this,
                // 日期时间毫秒 -> "yyyy-MM-dd HH:mm:ss"
                SerializerFeature.WriteDateUseDateFormat,
                // 输出值为null的字段
                SerializerFeature.WriteMapNullValue,
                // 消除对同一对象循环引用
                SerializerFeature.DisableCircularReferenceDetect);
    }

}
