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
@Table(name = "tb_item_cat")
public class ItemCat extends BaseEntity implements Serializable {
    /**
     * 类目ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 父类目ID=0时，代表的是一级的类目
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 类目名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 状态。可选值:1(正常),2(删除)
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 该类目是否为父类目，1为true，0为false
     */
    @Column(name = "is_parent")
    private Boolean isParent;

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

    private static final long serialVersionUID = 1L;

}