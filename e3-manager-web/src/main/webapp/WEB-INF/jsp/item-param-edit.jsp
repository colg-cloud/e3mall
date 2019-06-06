<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemParamAddTable" class="itemParam" style="margin-left: 30px; padding: 5px;">
  <tr>
    <td>商品类目:</td>
    <td><a href="javascript:" class="easyui-linkbutton selectItemCat">选择类目</a>
      <input type="hidden" name="cid" style="width: 280px;"/>
    </td>
  </tr>
  <tr class="hide addGroupTr">
    <td>规格参数:</td>
    <td>
      <ul>
        <li><a href="javascript:" class="easyui-linkbutton addGroup">添加分组</a></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td></td>
    <td>
      <a href="javascript:" class="easyui-linkbutton submit">提交</a>
      <a href="javascript:" class="easyui-linkbutton close">关闭</a>
    </td>
  </tr>
</table>
<div class="itemParamAddTemplate" style="display: none;">
  <li class="param">
    <ul>
      <li>
        <input class="easyui-textbox" style="width: 150px;" name="group"/>
        <a href="javascript:" class="easyui-linkbutton addParam"
           data-options="title:'添加参数', plain:true, iconCls:'icon-add'">
        </a>
      </li>
      <li>
        <span>|-------</span><input style="width: 150px;" class="easyui-textbox" name="param"/>
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