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

    /**
     * 总记录数
     */
    private Long recordCount;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 结果集
     */
    private List<ItemSearch> itemSearchList;

    private static final long serialVersionUID = 1L;

    /// ----------------------------------------------------------------------------------------------------

    public static SearchResult ok(Long recordCount, List<ItemSearch> itemSearchList) {
        return ok(recordCount, null, itemSearchList);
    }

    /**
     * 构造全文检索返回结果
     *
     * @param recordCount
     * @param totalPage
     * @param itemSearchList
     * @return
     */
    public static SearchResult ok(Long recordCount, Integer totalPage, List<ItemSearch> itemSearchList) {
        return new SearchResult(recordCount, totalPage, itemSearchList);
    }

}
