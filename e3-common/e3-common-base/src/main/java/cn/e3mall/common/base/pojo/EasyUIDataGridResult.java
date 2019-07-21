package cn.e3mall.common.base.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.Page;

import cn.e3mall.common.base.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * EasyUI 表格数据格式
 *
 * <pre>
 * EasyUI 必须返回标准数据对象，含有 'total' 和 'rows' 属性。
 * </pre>
 *
 * @author colg
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class EasyUIDataGridResult extends BaseEntity implements Serializable {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 结果集
     */
    @JSONField(ordinal = 1)
    private List rows;

    private static final long serialVersionUID = 1L;

    /// ----------------------------------------------------------------------------------------------------

    /**
     * 构造表格数据
     *
     * @param data 数据
     * @return easyUIDataGridResult
     */
    public static EasyUIDataGridResult ok(Object data) {
        EasyUIDataGridResult easyUiDataGridResult = new EasyUIDataGridResult();
        if (data instanceof Page<?>) {
            Page page = (Page)data;
            easyUiDataGridResult.setRows(page.getResult())
                                .setTotal(page.getTotal());
        } else if (data instanceof List) {
            List list = (List)data;
            easyUiDataGridResult.setRows(list)
                                .setTotal((long)list.size());
        }
        return easyUiDataGridResult;
    }

}
