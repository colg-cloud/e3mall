package cn.e3mall.common.base.pojo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

import cn.e3mall.common.base.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * e3 商城自定义响应结构
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class E3Result extends BaseEntity implements Serializable {

    /** 成功状态 {@value} */
    public static final Integer OK = 200;
    /** 失败状态 {@value} */
    public static final Integer FAIL = 500;

    /** 响应业务状态 */
    private Integer status;

    /** 响应消息 */
    private String msg;

    /** 响应中的数据 */
    @JSONField(ordinal = 1)
    private Object data;

    private static final long serialVersionUID = 1L;

    /// ----------------------------------------------------------------------------------------------------

    public E3Result(Object data) {
        this.status = OK;
        this.data = data;
    }

    public static E3Result ok(Object data) {
        return new E3Result(data);
    }

    public static E3Result ok() {
        return new E3Result(null);
    }

    public static E3Result fail(Integer status, String msg) {
        return new E3Result(status, msg, null);
    }

    public static E3Result fail(String msg) {
        return new E3Result(FAIL, msg, null);
    }

    public static E3Result fail() {
        return new E3Result(FAIL, null, null);
    }

}
