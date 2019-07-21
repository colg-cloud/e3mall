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
@Table(name = "tb_item_desc")
public class ItemDesc extends BaseEntity implements Serializable {
    /**
     * 商品ID
     */
    @Id
    @Column(name = "item_id")
    private Long itemId;

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
     * 商品描述
     */
    @Column(name = "item_desc")
    private String itemDesc;

    private static final long serialVersionUID = 1L;

}