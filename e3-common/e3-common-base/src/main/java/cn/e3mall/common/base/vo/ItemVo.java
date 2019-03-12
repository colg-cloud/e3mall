package cn.e3mall.common.base.vo;

import cn.e3mall.common.base.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 商品信息
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ItemVo extends Item {
    
    /** 商品类目名称 */
    private String cname;

    private static final long serialVersionUID = 1L;

}
