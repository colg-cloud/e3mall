<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemParamList" class="easyui-datagrid"
       data-options="title:'商品规格模版列表', pagination:true, pageNumber:1, pageSize:30, rownumbers:true, fitColumns:true, striped:true">
</table>

<div id="itemParamEditWindow" class="easyui-window" style="width: 80%; height: 80%; padding: 10px;"
     data-options="title:'编辑商品规格模版', closed:true, modal:true, iconCls:'icon-save'">
</div>

<script>
  $(() => {
    // 加载表格
    $('#itemParamList').datagrid({
      url: '/manager/item/param/list',
      columns: [[
        {field: 'ck', checkbox: true},
        {field: 'id', title: '商品规格模版ID', width: 130},
        {field: 'itemCatId', title: '商品类目ID', width: 100},
        {field: 'itemCatName', title: '商品类目', width: 100},
        {field: 'paramData', title: '规格(只显示分组名称)', width: 100, formatter: E3.formatItemParamData},
        {field: 'created', title: '创建日期', width: 120, align: 'center'},
        {field: 'updated', title: '更新日期', width: 120, align: 'center'},
      ]],
      toolbar: [
        {
          text: '新增', iconCls: 'icon-add', handler: () => {
            E3.createWindow({
              title: '新增规格参数',
              url: '/manager/item-param-add'
            })
          }
        },
        {
          text: '编辑', iconCls: 'icon-edit', handler: () => {
            let ids = E3.getSelectionsIds('#itemParamList')
            if (ids.length === 0) {
              $.messager.alert('提示', '必须选择一个商品规格模版才能编辑!', 'warning')
              return
            }
            if (ids.indexOf(',') > 0) {
              $.messager.alert('提示', '只能选择一个商品规格模版!', 'warning')
              return
            }

            $('#itemParamEditWindow').window({
              href: '/manager/item-param-edit',
              onLoad: () => {
                // 回显数据
                let data = E3.getSelections('#itemParamList')[0]
                //回显商品规格
                let paramData = JSON.parse(data.paramData)
                console.log(paramData)
              }
            }).window('open')

            $.messager.alert('提示', '编辑功能尚未完成!', 'error')
            // TODO: colg [商品规格模版 - 编辑功能尚未完成]
          }
        },
        {
          text: '删除', iconCls: 'icon-cancel', handler: () => {
            let ids = E3.getSelectionsIds('#itemParamList')
            if (ids.length === 0) {
              $.messager.alert('提示', '未选中商品规格模版!', 'warning')
              return
            }
            $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的商品规格模版吗?', r => {
              if (r) {
                $.post('/manager/item/param/delete', {ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '删除商品规格模版成功!', 'info', () => {
                      $('#itemParamList').datagrid('reload')
                    })
                  }
                })
              }
            })
          }
        }
      ]
    })
  })
</script>