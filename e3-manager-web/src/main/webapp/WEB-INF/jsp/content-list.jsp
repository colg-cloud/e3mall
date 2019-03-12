<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="Nested Panel" data-options="width:'100%',minHeight:500,noheader:true,border:false" style="padding:10px;">
  <div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:false" style="width:250px;padding:5px">
      <ul id="contentCategoryTree" class="easyui-tree" data-options="url:'/manager/content/category/list',animate: true,lines:true,method : 'GET'">
      </ul>
    </div>
    <div data-options="region:'center'" style="padding:5px">
      <table class="easyui-datagrid" id="contentList"
             data-options="toolbar:contentListToolbar,fitColumns:true,rownumbers:true,singleSelect:false,collapsible:true,pagination:true,idField:'id',method:'get',pageSize:20,url:'/manager/content/query/list',queryParams:{categoryId:0}">
        <thead>
          <tr>
            <th data-options="field:'id',width:30">ID</th>
            <th data-options="field:'title',width:220">内容标题</th>
            <th data-options="field:'subTitle',width:200">内容子标题</th>
            <th data-options="field:'titleDesc',width:300">内容描述</th>
            <th data-options="field:'url',width:60,align:'center',formatter:E3.formatUrl">内容连接</th>
            <th data-options="field:'pic',width:50,align:'center',formatter:E3.formatUrl">图片</th>
            <th data-options="field:'pic2',width:50,align:'center',formatter:E3.formatUrl">图片2</th>
            <th data-options="field:'created',width:130,align:'center'">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center'">更新日期</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</div>
<script>
  $(function () {
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
  })
  var contentListToolbar = [{
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
  }, {
    text: '编辑',
    iconCls: 'icon-edit',
    handler: function () {
      var ids = E3.getSelectionsIds('#contentList')
      if (ids.length === 0) {
        $.messager.alert('提示', '必须选择一个内容才能编辑!')
        return
      }
      if (ids.indexOf(',') > 0) {
        $.messager.alert('提示', '只能选择一个内容!')
        return
      }
      E3.createWindow({
    	title: '编辑内容',
        url: '/manager/content-edit',
        onLoad: function () {
          var data = $('#contentList').datagrid('getSelections')[0]
          $('#contentEditForm').form('load', data)

          // 实现图片
          if(data.pic){
            $("#contentEditForm [name=pic]").after("<a href='" + data.pic + "' target='_blank'><img src='" + data.pic + "' width='80' height='50'/></a>");
          }
          if(data.pic2){
            $("#contentEditForm [name=pic2]").after("<a href='"+data.pic2 + "' target='_blank'><img src='" + data.pic2 + "' width='80' height='50'/></a>");
          }

          contentEditEditor.html(data.content)
        }
      })
    }
  }, {
    text: '删除',
    iconCls: 'icon-cancel',
    handler: function () {
      var ids = E3.getSelectionsIds('#contentList')
      if (ids.length === 0) {
        $.messager.alert('提示', '未选中商品!')
        return
      }
      var categoryId = E3.getSelectionsCategoryId('#contentList')
      $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的内容吗？', function (r) {
        if (r) {
          var params = {'ids': ids, 'categoryId': categoryId}
          $.post('/manager/content/delete', params, function (data) {
            if (data.status === 200) {
              $.messager.alert('提示', '删除内容成功!', undefined, function () {
                $('#contentList').datagrid('reload')
              })
            }
          })
        }
      })
    }
  }]
</script>