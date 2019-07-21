package cn.e3mall.common.base.vo;

import java.io.Serializable;
import java.util.Date;

import cn.e3mall.common.base.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 商品规格参数vo
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ItemParamVo extends BaseEntity implements Serializable {

    /**
     * 商品规格参数id
     */
    private String id;
    /**
     * 商品类目id
     */
    private String itemCatId;
    /**
     * 商品类目名称
     */
    private String itemCatName;
    /**
     * 商品规格参数
     */
    private String paramData;
    /**
     * 创建日期
     */
    private Date created;
    /**
     * 更新日期
     */
    private Date updated;

    private static final long serialVersionUID = 1L;
}
