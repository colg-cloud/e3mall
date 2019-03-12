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
 * EasyUI 商品类目树形节点
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class EasyUiTreeNode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点id */
    private Long id;

    /** 节点名称 */
    private String text;

    /** 状态: open(展开), closed(关闭) */
    @JSONField(ordinal = 1)
    private String state;

}
