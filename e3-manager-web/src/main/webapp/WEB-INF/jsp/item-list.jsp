<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="itemList" title="商品列表"
       data-options="singleSelect:false,collapsible:true,fitColumns:true,striped:true,idField:'id',rownumbers:true,pagination:true,url:'/manager/item/list',method:'get',pageSize:30,toolbar:toolbar">
  <thead>
    <tr>
      <th data-options="field:'ck',checkbox:true"></th>
      <th data-options="field:'id'">商品ID</th>
      <th data-options="field:'title',width:280">商品标题</th>
      <th data-options="field:'cid',width:50">类目ID</th>
      <th data-options="field:'cname',width:100">类目名称</th>
      <th data-options="field:'sellPoint',width:380">卖点</th>
      <th data-options="field:'price',width:70,align:'right',formatter:E3.formatPrice">价格</th>
      <th data-options="field:'num',width:70,align:'right'">库存数量</th>
      <th data-options="field:'barcode',width:50">条形码</th>
      <th data-options="field:'status',width:60,align:'center',formatter:E3.formatItemStatus">状态</th>
      <th data-options="field:'created',width:120,align:'center'">创建日期</th>
      <th data-options="field:'updated',width:120,align:'center'">更新日期</th>
    </tr>
  </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑商品" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/manager/item-edit'"
     style="width:80%;height:80%;padding:10px;">
</div>
<script>
  var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
      $('.tree-title:contains("新增商品")').parent().click()
    }
  }, {
    text: '编辑',
    iconCls: 'icon-edit',
    handler: function () {
      var ids = E3.getSelectionsIds('#itemList')
      if (ids.length === 0) {
        $.messager.alert('提示', '必须选择一个商品才能编辑!')
        return
      }
      if (ids.indexOf(',') > 0) {
        $.messager.alert('提示', '只能选择一个商品!')
        return
      }

      $('#itemEditWindow').window({
        onLoad: function () {
          // 回显数据
          var data = $('#itemList').datagrid('getSelections')[0]
          data.priceView = E3.formatPrice(data.price)
          $('#itemeEditForm').form('load', data)

          // 加载商品描述
          $.getJSON('/manager/item/desc/' + data.id, function (_data) {
            if (_data.status === 200) {
              // UM.getEditor('itemeEditDescEditor').setContent(_data.data.itemDesc, false);
              itemEditEditor.html(_data.data.itemDesc)
            }
          })

          //加载商品规格
          $.getJSON('/manager/item/param/item/select/' + data.id, function (_data) {
            if (_data && _data.status === 200 && _data.data && _data.data.paramData) {
              $('#itemeEditForm .params').show()
              $('#itemeEditForm [name=itemParams]').val(_data.data.paramData)
              $('#itemeEditForm [name=itemParamId]').val(_data.data.id)

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
              $('#itemeEditForm .params td').eq(1).html(html)
            }
          })

          E3.init({
            'pics': data.image,
            'cid': data.cid,
            'cname': data.cname,
            fun: function (node) {
              E3.changeItemParam(node, 'itemeEditForm')
            }
          })
        }
      }).window('open')
    }
  }, {
    text: '删除',
    iconCls: 'icon-cancel',
    handler: function () {
      var ids = E3.getSelectionsIds('#itemList')
      if (ids.length === 0) {
        $.messager.alert('提示', '未选中商品!')
        return
      }
      $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的商品吗？', function (r) {
        if (r) {
          var params = {'ids': ids}
          $.post('/manager/item/delete', params, function (data) {
            if (data.status === 200) {
              $.messager.alert('提示', '删除商品成功!', undefined, function () {
                $('#itemList').datagrid('reload')
              })
            }
          })
        }
      })
    }
  }, '-', {
    text: '下架',
    iconCls: 'icon-remove',
    handler: function () {
      var ids = E3.getSelectionsIds('#itemList')
      if (ids.length === 0) {
        $.messager.alert('提示', '未选中商品!')
        return
      }
      $.messager.confirm('确认', '确定下架ID为 ' + ids + ' 的商品吗？', function (r) {
        if (r) {
          var params = {'ids': ids}
          $.post('/manager/item/instock', params, function (data) {
            if (data.status === 200) {
              $.messager.alert('提示', '下架商品成功!', undefined, function () {
                $('#itemList').datagrid('reload')
              })
            }
          })
        }
      })
    }
  }, {
    text: '上架',
    iconCls: 'icon-remove',
    handler: function () {
      var ids = E3.getSelectionsIds('#itemList')
      if (ids.length === 0) {
        $.messager.alert('提示', '未选中商品!')
        return
      }
      $.messager.confirm('确认', '确定上架ID为 ' + ids + ' 的商品吗？', function (r) {
        if (r) {
          var params = {'ids': ids}
          $.post('/manager/item/reshelf', params, function (data) {
            if (data.status === 200) {
              $.messager.alert('提示', '上架商品成功!', undefined, function () {
                $('#itemList').datagrid('reload')
              })
            }
          })
        }
      })
    }
  }]
</script>