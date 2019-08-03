<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" style="min-height: 500px;"
     data-options="noheader:true, border:false">
  <div class="easyui-layout"
       data-options="fit:true">
    <div style="padding: 5px; width: 200px;"
         data-options="region:'west', split:false">
      <ul id="contentCategoryTree"></ul>
    </div>
    <div style="padding: 5px;"
         data-options="region:'center'">
      <table id="contentList"
             data-options="title:'内容列表', rownumbers:true, fitColumns:true, striped:true, autoRowHeight:false, nowrap:true, pagination:true, pageNumber:1, pageSize:20, idField: 'id'">
      </table>
    </div>
  </div>
</div>

<script>
  $('#contentCategoryTree').tree({
    url: 'content/category/list',
    animate: true,
    lines: true,
    onClick: function (node) {
      const {target, id: categoryId} = node
      if ($(this).tree('isLeaf', target)) {
        // 重新加载行, 就像 load 方法一样，但是保持在当前页
        $('#contentList').datagrid('reload', {categoryId})
      }
    }
  })

  // 加载表格
  $('#contentList').datagrid({
    url: 'content/query/list',
    queryParams: {categoryId: 0},
    columns: [[
      {field: 'ck', checkbox: true},
      {field: 'id', title: 'ID', width: 80, sortable: true},
      {field: 'title', title: '内容标题', width: 200, sortable: true},
      {field: 'subTitle', title: '内容子标题', width: 200, sortable: true},
      {field: 'titleDesc', title: '内容描述', width: 300, sortable: true},
      {field: 'url', title: '内容连接', width: 80, align: 'center', formatter: E3.formatUrl},
      {field: 'pic', title: '图片', width: 80, align: 'center', formatter: E3.formatUrl},
      {field: 'pic2', title: '图片2', width: 80, align: 'center', formatter: E3.formatUrl},
      {field: 'created', title: '创建日期', width: 150, align: 'center', sortable: true},
      {field: 'created', title: '更新日期', width: 150, align: 'center', sortable: true}
    ]],
    sortName: 'updated',
    sortOrder: 'desc',
    toolbar: [
      {
        text: '新增', iconCls: 'icon-add', handler: () => {
          let $contentCategoryTree = $('#contentCategoryTree')
          let node = $contentCategoryTree.tree('getSelected')
          if (node && $contentCategoryTree.tree('isLeaf', node.target)) {
            E3.createWindow({
              title: '新增内容',
              url: 'content/add'
            })
          } else {
            $.messager.alert('提示', '新增内容必须选择一个内容分类', 'warning')
          }
        }
      },
      {
        text: '编辑', iconCls: 'icon-edit', handler: () => {
          const contentList = E3.getSelections('#contentList')
          if (!contentList.length) {
            $.messager.alert('提示', '必须选择一个内容才能编辑', 'warning')
          } else if (contentList.length > 1) {
            $.messager.alert('提示', '只能选择一个内容!', 'warning')
          } else {
            E3.createWindow({
              title: '编辑内容',
              url: 'content-edit',
              onLoad: () => {
                let data = E3.getSelections('#contentList')[0]
                $('#contentEditForm').form('load', data)

                // 实现图片
                if (data.pic) {
                  $('#contentEditForm [name=pic]').after('<a href=\'' + data.pic + '\' target=\'_blank\'><img src=\'' + data.pic + '\' width=\'80\' height=\'50\' alt=\'\'/></a>')
                }
                if (data.pic2) {
                  $('#contentEditForm [name=pic2]').after('<a href=\'' + data.pic2 + '\' target=\'_blank\'><img src=\'' + data.pic2 + '\' width=\'80\' height=\'50\' alt=\'\'/></a>')
                }

                contentEditEditor.html(data.content)
              }
            })
          }
        }
      },
      {
        text: '删除', iconCls: 'icon-remove', handler: () => {
          let ids = E3.getSelectionsIds('#contentList')
          if (ids.length === 0) {
            $.messager.alert('提示', '未选中商品!', 'warning')
            return
          }
          let categoryId = E3.getSelectionsCategoryId('#contentList')
          $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的内容吗？', r => {
            if (r) {
              $.post('content/delete', {categoryId, ids}, data => {
                if (data.status === 200) {
                  $.messager.alert('提示', '删除内容成功!', 'info', () => {
                    $('#contentList').datagrid('reload')
                  })
                }
              })
            }
          })
        }
      },
      {
        text: '刷新', iconCls: 'icon-reload', handler: () => {
          $('#contentList').datagrid('reload')
          $('#contentList').datagrid('clearSelections')
        }
      }
    ]
  })
</script>