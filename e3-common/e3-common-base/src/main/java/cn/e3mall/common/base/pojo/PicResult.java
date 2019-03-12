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

	private static final long serialVersionUID = 1L;

	/** 上传图片返回值, 成功(0), 失败(1) */
	private Integer error;

	/** 回显图片使用的url */
	private String url;

	/** 错误时的错误消息 */
	private String message;
	
	/**
	 * 上传成功
	 *
	 * @param url
	 * @return
	 */
	public static PicResult ok(String url) {
	    return new PicResult(0, url, null);
	}
	
	/**
	 * 上传失败
	 *
	 * @param message
	 * @return
	 */
	public static PicResult fail(String message) {
	    return new PicResult(1, null, message);
	}

}
