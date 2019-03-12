package cn.e3mall.common.freemarker.model;

import cn.e3mall.common.base.core.BaseEntity;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Student extends BaseEntity implements Serializable {

    private Integer id;
    private String name;
    private Integer age;
    private String address;

    private static final long serialVersionUID = 1L;

}
