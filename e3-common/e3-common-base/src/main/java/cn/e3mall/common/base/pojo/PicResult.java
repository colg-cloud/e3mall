package cn.e3mall.common.base.pojo;

import java.io.Serializable;

import cn.e3mall.common.base.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 上传图片返回信息
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PicResult extends BaseEntity implements Serializable {

    /**
     * 上传图片响应状态
     */
    private Integer status;

    /**
     * 回显图片使用的url
     */
    private String url;

    /**
     * 错误时的错误消息
     */
    private String message;

    private static final long serialVersionUID = 1L;

    /// ----------------------------------------------------------------------------------------------------

    /**
     * 上传成功
     *
     * @param url url
     * @return PicResult
     */
    public static PicResult ok(String url) {
        return new PicResult(E3Result.OK, url, null);
    }

    /**
     * 上传失败
     *
     * @param message 消息
     * @return PicResult
     */
    public static PicResult fail(String message) {
        return new PicResult(E3Result.FAIL, null, message);
    }

}
