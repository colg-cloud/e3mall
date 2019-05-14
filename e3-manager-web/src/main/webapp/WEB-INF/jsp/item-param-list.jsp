<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="itemParamList" title="商品规格模版列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,fitColumns:true,idField:'id',url:'/manager/item/param/list',method:'get',pageSize:30,toolbar:itemParamListToolbar">
  <thead>
    <tr>
      <th data-options="field:'ck',checkbox:true"></th>
      <th data-options="field:'id',width:100">商品规格模版ID</th>
      <th data-options="field:'itemCatId',width:100">商品类目ID</th>
      <th data-options="field:'itemCatName',width:100">商品类目</th>
      <th data-options="field:'paramData',width:100,formatter:E3.formatItemParamData">规格(只显示分组名称)</th>
      <th data-options="field:'created',width:100,align:'center'">创建日期</th>
      <th data-options="field:'updated',width:100,align:'center'">更新日期</th>
    </tr>
  </thead>
</table>
<div id="itemParamEditWindow" class="easyui-window" title="编辑商品规格模版" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/manager/item-param-edit'"
     style="width:80%;height:80%;padding:10px;">
</div>
<script>
  function getSelectionsIds() {
    var itemList = $('#itemParamList')
    var sels = itemList.datagrid('getSelections')
    var ids = []
    for (var i in sels) {
      ids.push(sels[i].id)
    }
    return ids.join(',')
  }

  var itemParamListToolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
      E3.createWindow({
        title: '新增规格参数',
        url: '/manager/item-param-add'
      })
    }
  }, {
    text: '编辑',
    iconCls: 'icon-edit',
    handler: function () {
      var ids = E3.getSelectionsIds('#itemParamList')
      if (ids.length === 0) {
        $.messager.alert('提示', '必须选择一个商品规格模版才能编辑!')
        return
      }
      if (ids.indexOf(',') > 0) {
        $.messager.alert('提示', '只能选择一个商品规格模版!')
        return
      }
      $.messager.alert('提示', '编辑功能尚未完成!')
      return
      // TODO: colg [商品规格模版 - 编辑功能尚未完成]
      
      $('#itemParamEditWindow').window({
        onLoad: function () {
          // 回显数据
          var data = $('#itemParamList').datagrid('getSelections')[0]
          console.log(data)

          //加载商品规格
          $.getJSON('/manager/item/param/item/select/' + data.id, function (_data) {
            if (_data && _data.status === 200 && _data.data && _data.data.paramData) {
              $('#itemEditForm .params').show()
              $('#itemEditForm [name=itemParams]').val(_data.data.paramData)
              $('#itemEditForm [name=itemParamId]').val(_data.data.id)

              //回显商品规格
              var paramData = JSON.parse(_data.data.paramData)

              var html = '<ul style="margin-left: -40px">'
              paramData.forEach(function (pd) {
                html += '<li><table>'
                html += '<tr><td colspan="2" class="group">' + pd.group + '</td></tr>'

                pd.params.forEach(function (ps) {
                    html += '<tr><td class="param"><span>' + ps.k + '</span>: </td><td><input class="easyui-textbox" style="width: 200px;" type="text" value="' + ps.v + '"/></td></tr>'
                  })
                html += '</table></li>'
              })
              html += '</ul>'
              $('#itemEditForm .params td').eq(1).html(html)
            }
          })

          E3.init({
            'pics': data.image,
            'cid': data.cid,
            fun: function (node) {
              E3.changeItemParam(node, 'itemEditForm')
            }
          })
        }
      }).window('open')
    }
  }, {
    text: '删除',
    iconCls: 'icon-cancel',
    handler: function () {
      var ids = E3.getSelectionsIds('#itemParamList')
      if (ids.length === 0) {
        $.messager.alert('提示', '未选中商品规格模版!')
        return
      }
      $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的商品规格模版吗？', function (r) {
        if (r) {
          var params = {'ids': ids}
          $.post('/manager/item/param/delete', params, function (data) {
            if (data.status === 200) {
              $.messager.alert('提示', '删除商品规格模版成功!', undefined, function () {
                $('#itemParamList').datagrid('reload')
              })
            }
          })
        }
      })
    }
  }]
</script>