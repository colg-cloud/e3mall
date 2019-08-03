<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../common/kindeditor.jsp" %>
<div style="padding: 5px">
  <form id="contentAddForm" class="itemForm" method="post">
    <input type="hidden" name="categoryId"/>
    <table style="padding: 10px;">
      <tr>
        <td>内容标题:</td>
        <td>
          <input class="easyui-textbox" type="text" name="title" style="width: 300px;"
                 data-options="required:true"/>
        </td>
      </tr>
      <tr>
        <td>内容子标题:</td>
        <td>
          <input class="easyui-textbox" type="text" name="subTitle" style="width: 300px;"/>
        </td>
      </tr>
      <tr>
        <td>内容描述:</td>
        <td>
          <input class="easyui-textbox" name="titleDesc" style="width: 300px; height: 80px;"
                 data-options="multiline:true, validType:'length[0,150]'"/>
        </td>
      </tr>
      <tr>
        <td>URL:</td>
        <td>
          <input class="easyui-textbox" type="text" name="url" style="width: 300px;"/>
        </td>
      </tr>
      <tr>
        <td>图片:</td>
        <td>
          <input type="hidden" name="pic"/>
          <a href="javascript:" class="easyui-linkbutton onePicUpload">图片上传</a>
        </td>
      </tr>
      <tr>
        <td>图片2:</td>
        <td>
          <input type="hidden" name="pic2"/>
          <a href="javascript:" class="easyui-linkbutton onePicUpload">图片上传</a>
        </td>
      </tr>
      <tr>
        <td>内容:</td>
        <td>
          <textarea name="content" style="width: 800px; height: 300px; visibility: hidden;"></textarea>
        </td>
      </tr>
    </table>
  </form>
  <div style="padding:5px">
    <a href="javascript:submitForm()" class="easyui-linkbutton"
       data-options="iconCls:'icon-ok'">提交
    </a>
    <a href="javascript:clearForm()" class="easyui-linkbutton"
       data-options="iconCls:'icon-no'">重置
    </a>
  </div>
</div>

<script>
  var contentAddEditor
  $(() => {
    contentAddEditor = E3.createEditor('#contentAddForm [name="content"]')
    E3.initOnePicUpload()
    $('#contentAddForm [name="categoryId"]').val($('#contentCategoryTree').tree('getSelected').id)
  })

  function submitForm() {
    let $contentAddForm = $('#contentAddForm')
    if ($contentAddForm.form('validate')) {
      contentAddEditor.sync()
      $.post('content/save', $contentAddForm.serialize(), data => {
        if (data.status === 200) {
          $.messager.alert('提示', '新增内容成功', 'info', () => {
            E3.closeCurrentWindow()
            $('#contentList').datagrid('reload')
          })
        }
      })
    } else {
      $.messager.alert('提示', '表单还未填写完成', 'warning')
    }
  }

  function clearForm() {
    $('#contentAddForm').form('reset')
    contentAddEditor.html('')
  }

</script>
