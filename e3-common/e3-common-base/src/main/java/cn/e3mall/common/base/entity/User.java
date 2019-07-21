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
@Table(name = "tb_user")
public class User extends BaseEntity implements Serializable {
    /**
     * 用户id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码，加密存储
     */
    @Column(name = "password")
    private String password;

    /**
     * 注册手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 注册邮箱
     */
    @Column(name = "email")
    private String email;

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