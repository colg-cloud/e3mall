<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemParamList"
       data-options="title:'商品规格模版列表', rownumbers:true, fitColumns:true, striped:true, autoRowHeight:false, nowrap:true, pagination:true, pageNumber:1, pageSize:20, idField: 'id'">
</table>

<div id="itemParamEditWindow" class="easyui-window" style="width: 80%; height: 80%; padding: 10px;"
     data-options="title:'编辑商品规格模版', closed:true, modal:true, iconCls:'icon-save'">
</div>

<script>
  $(() => {
    // 加载表格
    $('#itemParamList').datagrid({
      url: 'item/param/list',
      columns: [[
        {field: 'ck', checkbox: true},
        {field: 'id', title: '商品规格模版ID', width: 130, sortable: true},
        {field: 'itemCatId', title: '商品类目ID', width: 100},
        {field: 'itemCatName', title: '商品类目', width: 100},
        {field: 'paramData', title: '规格(只显示分组名称)', width: 100, formatter: E3.formatItemParamData},
        {field: 'created', title: '创建日期', width: 150, align: 'center', sortable: true},
        {field: 'updated', title: '更新日期', width: 150, align: 'center', sortable: true},
      ]],
      sortName: "updated",
      sortOrder: "desc",
      toolbar: [
        {
          text: '新增', iconCls: 'icon-add', handler: () => {
            E3.createWindow({
              title: '新增规格参数',
              url: 'item-param/add'
            })
          }
        },
        {
          text: '编辑', iconCls: 'icon-edit', handler: () => {
            let itemParamList = E3.getSelections('#itemParamList')
            if (!itemParamList.length) {
              $.messager.alert('提示', '必须选择一个商品规格模版才能编辑', 'warning')
            } else if (itemParamList.length > 1) {
              $.messager.alert('提示', '只能选择一个商品规格模版', 'warning')
            } else {
              E3.createWindow({
                title: '编辑商品规格模版',
                url: 'item-param/edit',
                onLoad: () => {
                  // 回显数据
                  let data = E3.getSelections('#itemParamList')[0]
                  //回显商品规格
                  let paramData = JSON.parse(data.paramData)
                  console.log(paramData)
                }
              })
            }
            $.messager.alert('提示', '编辑商品规格模版, 未完成', 'question')
          }
        },
        {
          text: '删除', iconCls: 'icon-remove', handler: () => {
            let ids = E3.getSelectionsIds('#itemParamList')
            if (ids.length) {
              $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的商品规格模版吗?', r => {
                if (r) {
                  $.post('item/param/delete', {ids}, data => {
                    if (data.status === 200) {
                      $.messager.alert('提示', '删除商品规格模版成功', 'info', () => {
                        $('#itemParamList').datagrid('reload')
                      })
                    }
                  })
                }
              })
            } else {
              $.messager.alert('提示', '未选中商品规格模版!', 'warning')
            }
          }
        },
        {
          text: '刷新', iconCls: 'icon-reload', handler: () => {
            $('#itemParamList').datagrid('reload')
            $('#itemParamList').datagrid('clearSelections')
          }
        }
      ]
    })
  })
</script>