package cn.e3mall.common.solr;

import java.io.Serializable;

import cn.e3mall.common.base.core.BaseEntity;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 全文检索 - 商品信息
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ItemSearch extends BaseEntity implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String title;
    /**
     * 卖点
     */
    private String sellPoint;
    /**
     * 价格
     */
    private Long price;
    /**
     * 图片路径
     */
    private String image;
    /**
     * 类目名称
     */
    private String categoryName;

    private static final long serialVersionUID = 1L;

    /// ----------------------------------------------------------------------------------------------------

    /**
     * 获取图片数组
     *
     * @return
     */
    public String[] getImages() {
        return StrUtil.split(this.image, ",");
    }

}
