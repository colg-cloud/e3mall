<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemList"
       data-options="title:'商品列表', rownumbers:true, fitColumns:true, striped:true, autoRowHeight:false, nowrap:true, pagination:true, pageNumber:1, pageSize:20, idField: 'id'">
</table>

<script>
  $('#itemList').datagrid({
    url: 'item/list',
    columns: [[
      {field: 'ck', checkbox: true},
      {field: 'id', title: '商品ID', width: 130, sortable: true},
      {field: 'title', title: '商品标题', width: 280, sortable: true},
      {field: 'cid', title: '商品类目ID', width: 100, sortable: true},
      {field: 'cname', title: '商品类目名称', width: 100},
      {field: 'sellPoint', title: '卖点', width: 380},
      {field: 'price', title: '商品价格', width: 80, align: 'right', formatter: val => (val / 100).toFixed(2), sortable: true},
      {field: 'num', title: '库存数量', width: 70, align: 'right', sortable: true},
      {field: 'barcode', title: '条形码', width: 50},
      {field: 'status', title: '状态', width: 60, align: 'center', formatter: E3.formatItemStatus, sortable: true},
      {field: 'created', title: '创建日期', width: 150, sortable: true},
      {field: 'updated', title: '更新日期', width: 150, sortable: true},
    ]],
    sortName: "updated",
    sortOrder: "desc",
    toolbar: [
      {
        text: '新增', iconCls: 'icon-add', handler: () => {
          $('.tree-title:contains("新增商品")').parent().click()
        }
      },
      {
        text: '编辑', iconCls: 'icon-edit', handler: () => {
          const itemList = E3.getSelections('#itemList')
          if (!itemList.length) {
            $.messager.alert('提示', '必须选择一个商品才能编辑', 'warning')
          } else if (itemList.length > 1) {
            $.messager.alert('提示', '只能选择一个商品', 'warning')
          } else {
            E3.createWindow({
              title: '编辑商品',
              url: 'item/edit',
              onLoad: () => {
                // 回显数据
                let data = E3.getSelections('#itemList')[0]
                const {id, price, cid, cname, image: pics} = data
                data['priceView'] = E3.formatPrice(price)
                $('#itemEditForm').form('load', data)

                // 加载商品描述
                $.getJSON('item/desc/' + id, data => {
                  if (data.status === 200) {
                    const {itemDesc} = data.data
                    itemEditEditor.html(itemDesc)
                  }
                })

                //加载商品规格
                $.getJSON('item/param/item/select/' + id, data => {
                  if (data.status === 200 && data.data && data.data.paramData) {
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
                        html +=
                            '<tr>' +
                            '  <td class="param"><span>' + k + '</span>: </td>' +
                            '  <td><input type="text" value="' + v + '" style="width: 200px; height: 28px; border: 1px solid #95B8E7; border-radius: 5px;"/></td>' +
                            '</tr>'
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
            })
          }
        }
      },
      {
        text: '删除', iconCls: 'icon-remove', handler: () => {
          const ids = E3.getSelectionsIds('#itemList')
          if (ids.length) {
            $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的商品吗?', r => {
              if (r) {
                $.post('item/delete', {ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '删除商品成功', 'info', () => {
                      $('#itemList').datagrid('reload')
                    })
                  }
                })
              }
            })
          } else {
            $.messager.alert('提示', '未选中商品', 'warning')
          }
        }
      },
      {
        text: '下架', iconCls: 'icon-undo', handler: () => {
          const ids = E3.getSelectionsIds('#itemList')
          if (ids.length) {
            $.messager.confirm('提示', '确定下架ID为 ' + ids + ' 的商品吗?', r => {
              if (r) {
                $.post('item/inStock', {ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '下架商品成功', 'info', () => {
                      $('#itemList').datagrid('reload')
                    })
                  }
                })
              }
            })
          } else {
            $.messager.alert('提示', '未选中商品', 'warning')
          }
        }
      },
      {
        text: '上架', iconCls: 'icon-redo', handler: () => {
          const ids = E3.getSelectionsIds('#itemList')
          if (ids.length) {
            $.messager.confirm('提示', '确定上架ID为 ' + ids + ' 的商品吗?', r => {
              if (r) {
                $.post('item/reShelf', {ids}, data => {
                  if (data.status === 200) {
                    $.messager.alert('提示', '上架商品成功!', 'info', () => {
                      $('#itemList').datagrid('reload')
                    })
                  }
                })
              }
            })
          } else {
            $.messager.alert('提示', '未选中商品', 'warning')
          }
        }
      },
      {
        text: '刷新', iconCls: 'icon-reload', handler: () => {
          $('#itemList').datagrid('reload')
          $('#itemList').datagrid('clearSelections')
        }
      }
    ]
  })
</script>