<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div>
  <a href="javascript:" class="easyui-linkbutton" onclick="importItems()"
     data-options="plain:true, iconCls:'icon-print'">一键导入所有商品数据到索引库
  </a>
</div>

<script>
  function importItems() {
    $.messager.confirm('确认', '确定一键导入所有商品数据到索引库吗?', r => {
      if (r) {
        $.messager.progress({
          title: '提示',
          interval: '100'
        })
        $.post('item/import', data => {
          $.messager.progress('close')
          if (data.status === 200) {
            $.messager.alert('提示', '导入索引库成功, 耗时: ' + data.data['spendMs'] + 'ms !', 'info')
          } else {
            $.messager.alert('提示', '导入索引库失败', 'warning')
          }
        })
      }
    })
  }
</script>