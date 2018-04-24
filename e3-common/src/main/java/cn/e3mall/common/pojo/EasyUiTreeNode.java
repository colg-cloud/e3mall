package cn.e3mall.common.pojo;

import java.io.Serializable;

import cn.e3mall.common.core.BaseEntity;

/**
 * EasyUI 商品类目树形节点
 *
 * @author colg
 */
public class EasyUiTreeNode extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String text;
	private String state;

	public EasyUiTreeNode() {
	}

	public EasyUiTreeNode(Long id, String text, String state) {
		this.id = id;
		this.text = text;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
