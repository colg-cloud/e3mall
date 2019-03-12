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
@Table(name = "tb_content")
public class Content extends BaseEntity implements Serializable {
    /**
     * 内容表ID
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 内容类目ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 内容标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 子标题
     */
    @Column(name = "sub_title")
    private String subTitle;

    /**
     * 标题描述
     */
    @Column(name = "title_desc")
    private String titleDesc;

    /**
     * 链接
     */
    @Column(name = "url")
    private String url;

    /**
     * 图片绝对路径
     */
    @Column(name = "pic")
    private String pic;

    /**
     * 图片2
     */
    @Column(name = "pic2")
    private String pic2;

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
     * 内容
     */
    @Column(name = "content")
    private String content;

    private static final long serialVersionUID = 1L;

}