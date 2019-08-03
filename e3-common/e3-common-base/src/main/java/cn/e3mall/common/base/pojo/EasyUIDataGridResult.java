package cn.e3mall.common.base.pojo;

import cn.e3mall.common.base.core.BaseEntity;
import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * EasyUI 表格的数据格式
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
            Page<Object> page = (Page)data;
            easyUiDataGridResult.setRows(page.getResult())
                                .setTotal(page.getTotal());
        } else if (data instanceof List) {
            List<Object> list = (List)data;
            easyUiDataGridResult.setRows(list)
                                .setTotal((long)list.size());
        }
        return easyUiDataGridResult;
    }

}
