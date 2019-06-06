<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" style="padding: 10px;"
     data-options="width:'100%', minHeight:500, noheader:true, border:false">
  <div class="easyui-layout"
       data-options="fit:true">
    <div style="width: 250px; padding: 5px"
         data-options="region:'west', split:false">
      <ul id="contentCategoryTree" class="easyui-tree"
          data-options="url:'/manager/content/category/list',animate: true,lines:true,method : 'GET'">
      </ul>
    </div>
    <div style="padding: 5px"
         data-options="region:'center'">
      <table class="easyui-datagrid" id="contentList"
             data-options="title:'内容列表', pagination:true, pageNumber:1, pageSize:30, rownumbers:true, fitColumns:true, striped:true">
      </table>
    </div>
  </div>
</div>
<script>
  $(() => {
    var tree = $('#contentCategoryTree')
    var datagrid = $('#contentList')
    tree.tree({
      onClick: function (node) {
        if (tree.tree('isLeaf', node.target)) {
          datagrid.datagrid('reload', {
            categoryId: node.id
          })
        }
      }
    })

    // 加载表格
    $('#contentList').datagrid({
      url: '/manager/content/query/list',
      queryParams: {categoryId: 0},
      columns: [[
        {field: 'ck', checkbox: true},
        {field: 'id', title: 'ID', width: 30},
        {field: 'title', title: '内容标题', width: 220},
        {field: 'subTitle', title: '内容子标题', width: 200},
        {field: 'titleDesc', title: '内容描述', width: 300},
        {field: 'url', title: '内容连接', width: 60, align: 'center', formatter: E3.formatUrl},
        {field: 'pic', title: '图片', width: 50, align: 'center', formatter: E3.formatUrl},
        {field: 'pic2', title: '图片2', width: 50, align: 'center', formatter: E3.formatUrl},
        {field: 'created', title: '创建日期', width: 130, align: 'center'},
        {field: 'created', title: '更新日期', width: 130, align: 'center'}
      ]],
      toolbar: [
        {
          text: '新增',
          iconCls: 'icon-add',
          handler: function () {
            var node = $('#contentCategoryTree').tree('getSelected')
            if (!node || !$('#contentCategoryTree').tree('isLeaf', node.target)) {
              $.messager.alert('提示', '新增内容必须选择一个内容分类!')
              return
            }
            E3.createWindow({
              title: '新增内容',
              url: '/manager/content-add'
            })
          }
        },
        {
          text: '编辑', iconCls: 'icon-edit', handler: () => {
            let ids = E3.getSelectionsIds('#contentList')
            if (ids.length === 0) {
              $.messager.alert('提示', '必须选择一个内容才能编辑!', 'warning')
              return
            }
            if (ids.indexOf(',') > 0) {
              $.messager.alert('提示', '只能选择一个内容!', 'warning')
              return
            }
            E3.createWindow({
              title: '编辑内容',
              url: '/manager/content-edit',
              onLoad: () => {
                let data = $('#contentList').datagrid('getSelections')[0]
                $('#contentEditForm').form('load', data)

                // 实现图片
                if (data.pic) {
                  $("#contentEditForm [name=pic]").after("<a href='" + data.pic + "' target='_blank'><img src='" + data.pic + "' width='80' height='50'/></a>");
                }
                if (data.pic2) {
                  $("#contentEditForm [name=pic2]").after("<a href='" + data.pic2 + "' target='_blank'><img src='" + data.pic2 + "' width='80' height='50'/></a>");
                }

                contentEditEditor.html(data.content)
              }
            })
          }
        },
        {
          text: '删除', iconCls: 'icon-cancel', handler: () => {
            let ids = E3.getSelectionsIds('#contentList')
            if (ids.length === 0) {
              $.messager.alert('提示', '未选中商品!', 'warning')
              return
            }
            let categoryId = E3.getSelectionsCategoryId('#contentList')
            $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的内容吗？', r => {
              if (r) {
                $.post('/manager/content/delete', {categoryId, ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '删除内容成功!', 'info', () => {
                      $('#contentList').datagrid('reload')
                    })
                  }
                })
              }
            })
          }
        }]
    })
  })
</script>