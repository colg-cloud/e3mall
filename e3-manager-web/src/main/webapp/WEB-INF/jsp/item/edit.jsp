<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/kindeditor.jsp" %>
<div>
  <form id="itemEditForm" class="itemForm" method="post">
    <input type="hidden" name="id"/>
    <table style="padding: 10px;">
      <tr>
        <td>商品类目:</td>
        <td>
          <a href="javascript:" class="easyui-linkbutton selectItemCat">选择类目</a>
          <input type="hidden" name="cid" style="width: 300px;"/>
        </td>
      </tr>
      <tr>
        <td>商品标题:</td>
        <td>
          <input class="easyui-textbox" type="text" name="title" style="width: 300px;"
                 data-options="required:true"/>
        </td>
      </tr>
      <tr>
        <td>商品卖点:</td>
        <td>
          <input class="easyui-textbox" type="text" name="sellPoint" style="width: 300px; height: 80px;"
                 data-options="multiline:true, validType:'length[0,150]'"/>
        </td>
      </tr>
      <tr>
        <td>商品价格:</td>
        <td>
          <input class="easyui-numberbox" type="text" name="priceView"
                 data-options="required:true, min:1, max:99999999, precision:2"/>
          <input type="hidden" name="price"/>
        </td>
      </tr>
      <tr>
        <td>库存数量:</td>
        <td>
          <input class="easyui-numberbox" type="text" name="num"
                 data-options="required:true, min:1, max:99999999, precision:0"/>
        </td>
      </tr>
      <tr>
        <td>条形码:</td>
        <td>
          <input class="easyui-textbox" type="text" name="barcode"
                 data-options="validType:'length[1,30]'"/>
        </td>
      </tr>
      <tr>
        <td>商品图片:</td>
        <td>
          <a href="javascript:" class="easyui-linkbutton picFileUpload">上传图片</a>
          <input type="hidden" name="image"/>
        </td>
      </tr>
      <tr>
        <td>商品描述:</td>
        <td>
          <textarea name="desc" style="width: 800px; height: 300px; visibility: hidden;"></textarea>
        </td>
      </tr>
      <tr class="params hide">
        <td>商品规格:</td>
        <td></td>
      </tr>
    </table>
    <input type="hidden" name="itemParams"/>
    <input type="hidden" name="itemParamId"/>
  </form>
  <div style="padding:10px">
    <a href="javascript:submitForm()" class="easyui-linkbutton"
       data-options="iconCls:'icon-ok'">提交
    </a>
  </div>
</div>

<script>
  var itemEditEditor
  $(() => {
    //实例化编辑器
    itemEditEditor = E3.createEditor('#itemEditForm [name=desc]')
  })

  function submitForm() {
    let $itemEditForm = $('#itemEditForm')
    if ($itemEditForm.form('validate')) {
      $('#itemEditForm [name=price]').val(eval($('#itemEditForm [name=priceView]').val()) * 100)
      // 同步文本框中的商品描述
      itemEditEditor.sync()

      // 获取商品规格参数
      let paramData = E3.getItemParamData('#itemEditForm')
      $('#itemEditForm [name=itemParams]').val(paramData)

      $.post('item/update', $itemEditForm.serialize(), data => {
        if (data.status === 200) {
          $.messager.alert('提示', '修改商品成功', 'info', () => {
            E3.closeCurrentWindow()
            $('#itemList').datagrid('reload')
          })
        }
      })
    } else {
      $.messager.alert('提示', '表单还未填写完成', 'warning')
    }
  }
</script>