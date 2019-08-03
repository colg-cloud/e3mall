package cn.e3mall.common.base.pojo;

import cn.e3mall.common.base.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * EasyUI 树的数据格式
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class EasyUiTreeNodeResult extends BaseEntity implements Serializable {

    /**
     * 节点id, 它对于加载远程数据很重要
     */
    private Long id;

    /**
     * 节点文本
     */
    private String text;

    /**
     * 节点状态, 'open' 或 'closed', 默认是 'open'. 当设置为 'closed' 时, 该节点有子节点, 并且将从远程站点加载它们.
     */
    private String state;

    /**
     * 节点是否被选中
     */
    private Boolean checked;

    /**
     * 节点添加的自定义属性
     */
    private Map<String, Object> attributes;

    /**
     * 子节点的节点数组
     */
    private List<EasyUiTreeNodeResult> children;

    private static final long serialVersionUID = 1L;

}
