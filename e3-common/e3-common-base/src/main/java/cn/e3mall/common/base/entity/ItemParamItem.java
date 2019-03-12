package cn.e3mall.common.base.entity;

import cn.e3mall.common.base.core.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * - @mbg.generated
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "tb_item_param_item")
public class ItemParamItem extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 商品ID
     */
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    /**
     * 参数数据，格式为json格式
     */
    @Column(name = "param_data")
    private String paramData;

    private static final long serialVersionUID = 1L;

    /// ----------------------------------------------------------------------------------------------------

    public ItemParamItem(Long itemId, Date created, Date updated, String paramData) {
        this.itemId = itemId;
        this.created = created;
        this.updated = updated;
        this.paramData = paramData;
    }

}