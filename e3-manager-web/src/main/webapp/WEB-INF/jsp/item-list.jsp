<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemList" class="easyui-datagrid"
       data-options="title:'编辑商品'">
</table>

<div id="itemEditWindow" class="easyui-window" style="width: 80%; height: 80%; padding: 10px;"
     data-options="title:'编辑商品', closed:true">
</div>

<script>
  $(() => {
    // 加载表格
    $('#itemList').datagrid({
      url: '/manager/item/list',
      method: 'get',
      pagination: true,
      pageNumber: 1,
      pageSize: 30,
      rownumbers: true,
      fitColumns: true,
      striped: true,
      idField: 'id',
      columns: [[
        {field: 'ck', checkbox: true},
        {field: 'id', title: '商品ID', width: 180},
        {field: 'title', title: '商品标题', width: 280},
        {field: 'cid', title: '商品类目ID', width: 80},
        {field: 'cname', title: '商品类目名称', width: 100},
        {field: 'sellPoint', title: '卖点', width: 380},
        {field: 'price', title: '商品价格', width: 80, align: 'right', formatter: E3.formatPrice},
        {field: 'num', title: '库存数量', width: 70, align: 'right'},
        {field: 'barcode', title: '条形码', width: 50},
        {field: 'status', title: '状态', width: 60, align: 'center', formatter: E3.formatItemStatus},
        {field: 'created', title: '创建日期', width: 120, align: 'center'},
        {field: 'updated', title: '更新日期', width: 120, align: 'center'},
      ]],
      toolbar: [
        {
          text: '新增', iconCls: 'icon-add', handler: () => {
            $('.tree-title:contains("新增商品")').parent().click()
          }
        }, {
          text: '编辑', iconCls: 'icon-edit', handler: () => {
            let ids = E3.getSelectionsIds('#itemList')
            if (ids.length === 0) {
              $.messager.alert('提示', '必须选择一个商品才能编辑!', 'warning')
              return
            }
            if (ids.indexOf(',') > 0) {
              $.messager.alert('提示', '只能选择一个商品!', 'warning')
              return
            }

            $('#itemEditWindow').window({
              href: '/manager/item-edit',
              modal: true,
              iconCls: 'icon-save',
              onLoad: () => {
                // 回显数据
                let data = E3.getSelections('#itemList')[0]
                const {id, price, cid, cname, image: pics} = data
                data.priceView = E3.formatPrice(price)
                $('#itemEditForm').form('load', data)

                // 加载商品描述
                $.getJSON('/manager/item/desc/' + id, data => {
                  if (data.status === 200) {
                    const {itemDesc} = data.data
                    itemEditEditor.html(itemDesc)
                  }
                })

                //加载商品规格
                $.getJSON('/manager/item/param/item/select/' + id, data => {
                  if (data && data.status === 200 && data.data && data.data.paramData) {
                    $('#itemEditForm .params').show()
                    $('#itemEditForm [name=itemParams]').val(data.data.paramData)
                    $('#itemEditForm [name=itemParamId]').val(data.data.id)

                    //回显商品规格
                    let paramData = JSON.parse(data.data.paramData)

                    let html = '<ul style="margin-left: -40px">'
                    paramData.forEach(pd => {
                      const {group, params} = pd
                      html += '<li><table>'
                      html += '<tr><td colspan="2" class="group">' + group + '</td></tr>'

                      params.forEach(ps => {
                        const {k, v} = ps
                        html += '<tr><td class="param"><span>' + k + '</span>: </td><td><input class="easyui-textbox" style="width: 200px;" type="text" value="' + v + '"/></td></tr>'
                      })
                      html += '</table></li>'
                    })
                    html += '</ul>'
                    $('#itemEditForm .params td').eq(1).html(html)
                  }
                })

                E3.init({
                  pics,
                  cid,
                  cname,
                  fun: node => {
                    E3.changeItemParam(node, 'itemEditForm')
                  }
                })
              }
            }).window('open')
          }
        }, {
          text: '删除', iconCls: 'icon-cancel', handler: () => {
            let ids = E3.getSelectionsIds('#itemList')
            if (ids.length === 0) {
              $.messager.alert('提示', '未选中商品!')
              return
            }
            $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的商品吗？', r => {
              if (r) {
                $.post('/manager/item/delete', {ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '删除商品成功!', 'info', () => {
                      $('#itemList').datagrid('reload')
                    })
                  }
                })
              }
            })
          }
        }, {
          text: '下架', iconCls: 'icon-remove', handler: () => {
            let ids = E3.getSelectionsIds('#itemList')
            if (ids.length === 0) {
              $.messager.alert('提示', '未选中商品!')
              return
            }
            $.messager.confirm('确认', '确定下架ID为 ' + ids + ' 的商品吗？', r => {
              if (r) {
                $.post('/manager/item/instock', {ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '下架商品成功!', 'info', () => {
                      $('#itemList').datagrid('reload')
                    })
                  }
                })
              }
            })
          }
        }, {
          text: '上架', iconCls: 'icon-remove', handler: () => {
            let ids = E3.getSelectionsIds('#itemList')
            if (ids.length === 0) {
              $.messager.alert('提示', '未选中商品!')
              return
            }
            $.messager.confirm('确认', '确定上架ID为 ' + ids + ' 的商品吗？', r => {
              if (r) {
                $.post('/manager/item/reshelf', {ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '上架商品成功!', 'info', () => {
                      $('#itemList').datagrid('reload')
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