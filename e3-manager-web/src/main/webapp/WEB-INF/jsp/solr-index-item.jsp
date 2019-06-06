<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div>
  <a class="easyui-linkbutton" id="importItems"
     data-options="plain:true,iconCls:'icon-print'">一键导入所有商品数据到索引库
  </a>
</div>

<div id="p" style="width:400px;"></div>

<script>
  $(() => {
    $('#importItems').click(() => {
      $.messager.confirm('确认', '确定一键导入所有商品数据到索引库吗?', r => {
        if (r) {
          $.messager.progress({title: '提示', interval: '100'})
          $.post('/manager/item/import', null, function (data) {
            $.messager.progress('close')
            if (data.status === 200) {
              $.messager.alert('提示', '导入索引库成功！', 'info')
            } else {
              $.messager.alert('提示', '导入索引库失败！', 'warning')
            }
          })
        }
      })
    })
  })
</script>