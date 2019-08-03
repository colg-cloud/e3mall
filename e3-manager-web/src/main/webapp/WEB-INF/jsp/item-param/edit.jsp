<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemParamAddTable" class="itemParam" style="margin-left: 30px; padding: 5px;">
  <tr>
    <td>商品类目:</td>
    <td><a href="javascript:" class="easyui-linkbutton selectItemCat">选择类目</a>
      <input type="hidden" name="cid"/>
    </td>
  </tr>
  <tr class="hide addGroupTr">
    <td>规格参数:</td>
    <td>
      <ul>
        <li>
          <a href="javascript:" class="easyui-linkbutton addGroup" style="font-weight:bold;"
             data-options="plain:true, iconCls:'icon-add'">添加分组
          </a>
        </li>
      </ul>
    </td>
  </tr>
  <tr>
    <td></td>
    <td>
      <a href="javascript:" class="easyui-linkbutton submit"
         data-options="iconCls:'icon-ok'">提交
      </a>
      <a href="javascript:" class="easyui-linkbutton close"
         data-options="iconCls:'icon-no'">关闭
      </a>
    </td>
  </tr>
</table>
<div class="itemParamAddTemplate" style="display: none;">
  <li class="param" style="margin-top: 10px;">
    <ul>
      <li>
        <input class="easyui-textbox" name="group" style="width: 300px;"/>
        <a href="javascript:" class="easyui-linkbutton addParam" style="margin-left: 10px;"
           data-options="title:'添加参数', plain:true, iconCls:'icon-add'">
        </a>
      </li>
      <li style="margin-top: 2px;">
        <span>|-------</span>
        <input class="easyui-textbox" name="param" style="width: 300px;"/>
        <a href="javascript:" class="easyui-linkbutton delParam"
           data-options="title:'删除', plain:true, iconCls:'icon-cancel'">
        </a>
      </li>
    </ul>
  </li>
</div>

<script>
  $(() => {

  })
</script>