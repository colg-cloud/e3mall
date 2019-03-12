package cn.e3mall.common.solr;

import java.io.Serializable;
import java.util.List;

import cn.e3mall.common.base.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 全文检索 - 返回结果
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SearchResult extends BaseEntity implements Serializable {

    /** 总记录数 */
    private Long recourdCount;
    /** 总页数 */
    private Integer totalPages;
    /** 结果集 */
    private List<ItemSearch> itemSearchs;

    private static final long serialVersionUID = 1L;

    /// ----------------------------------------------------------------------------------------------------

    public static SearchResult ok(Long recourdCount, List<ItemSearch> itemSearchs) {
        return ok(recourdCount, null, itemSearchs);
    }

    /**
     * 构造全文检索返回结果
     *
     * @param recourdCount
     * @param totalPages
     * @param itemSearchs
     * @return
     */
    public static SearchResult ok(Long recourdCount, Integer totalPages, List<ItemSearch> itemSearchs) {
        return new SearchResult(recourdCount, totalPages, itemSearchs);
    }

}
