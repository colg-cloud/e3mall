<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common/kindeditor.jsp" %>
<div style="padding: 10px;">
  <form id="itemAddForm" class="itemForm" method="post">
    <table style="padding: 5px;">
      <tr>
        <td>商品类目:</td>
        <td>
          <a href="javascript:" class="easyui-linkbutton selectItemCat">选择类目</a>
          <input type="hidden" name="cid" style="width: 280px;"/>
        </td>
      </tr>
      <tr>
        <td>商品标题:</td>
        <td>
          <input class="easyui-textbox" type="text" name="title" data-options="required:true" style="width: 280px;"/>
        </td>
      </tr>
      <tr>
        <td>商品卖点:</td>
        <td>
          <input class="easyui-textbox" name="sellPoint" style="height: 60px; width: 280px;"
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
          <textarea name="desc" style="width: 800px; left: 300px; visibility: hidden;"></textarea>
        </td>
      </tr>
      <tr class="params hide">
        <td>商品规格:</td>
        <td></td>
      </tr>
    </table>
    <input type="hidden" name="itemParams"/>
  </form>
  <div style="padding:5px">
    <a href="javascript:" class="easyui-linkbutton" onclick="submitForm()">提交</a>
    <a href="javascript:" class="easyui-linkbutton" onclick="clearForm()">重置</a>
  </div>
</div>

<script>
  let itemAddEditor
  //页面初始化完毕后执行此方法
  $(() => {
    // 创建富文本编辑器
    itemAddEditor = E3.createEditor('#itemAddForm [name=desc]')
    // 初始化类目选择和图片上传器
    E3.init({
      fun: node => {
        // 根据商品分类id获取商品规格模版, 生成规格信息
        E3.changeItemParam(node, 'itemAddForm')
      }
    })
  })

  //提交表单
  function submitForm() {
    //有效性验证
    let $itemAddForm = $('#itemAddForm')
    if (!$itemAddForm.form('validate')) {
      $.messager.alert('提示', '表单还未填写完成!', 'warning')
      return
    }
    // 取商品价格, 单位为"分"
    $('#itemAddForm [name=price]').val(eval($('#itemAddForm [name=priceView]').val()) * 100)
    // 同步文本框中的商品描述
    itemAddEditor.sync()

    // 获取商品规格参数
    let paramData = E3.getItemParamData('#itemAddForm')
    $('#itemAddForm [name=itemParams]').val(paramData)

    // ajax的post方式提交表单
    // $("#itemAddForm").serialize()将表单序列号为key-value形式的字符串
    $.post('/manager/item/save', $itemAddForm.serialize(), data => {
      if (data.status === 200) {
        $.messager.alert('提示', '新增商品成功!', 'info')
        setTimeout(() => {
          window.location.href = '/manager/index'
        }, 500)
      }
    })
  }

  function clearForm() {
    $('#itemAddForm').form('reset')
    itemAddEditor.html('')
  }
</script>