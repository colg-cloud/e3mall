<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
  <a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'" onclick="importItems()">一键导入所有商品数据到索引库</a>
</div>
<script>
  function importItems() {
    $.messager.confirm('确认', '确定一键导入所有商品数据到索引库吗?', function (r) {
      if (r) {
        $.post('/manager/item/import', null, function (data) {
          if (data.status === 200) {
            $.messager.alert('提示', '导入索引库成功！', 'info')
          } else {
            $.messager.alert('提示', '导入索引库失败！', 'warning')
          }
        })
      }
    })
  }
</script>