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
@Table(name = "tb_item_param")
public class ItemParam extends BaseEntity implements Serializable {
    /**
     * 商品规格参数ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 商品类目ID
     */
    @Column(name = "item_cat_id")
    private Long itemCatId;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;

    /**
     * 更新时间
     */
    @Column(name = "updated")
    private Date updated;

    /**
     * 参数数据，格式为json格式
     */
    @Column(name = "param_data")
    private String paramData;

    private static final long serialVersionUID = 1L;

}