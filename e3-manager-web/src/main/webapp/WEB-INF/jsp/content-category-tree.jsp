<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
  <ul id="contentCategory" class="easyui-tree"></ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
  <div data-options="iconCls:'icon-add',name:'add'">添加</div>
  <div data-options="iconCls:'icon-edit',name:'edit'">重命名</div>
  <div class="menu-sep"></div>
  <div data-options="iconCls:'icon-remove',name:'remove'">删除</div>
</div>
<script>
  $(function () {
    // 初始化一个 tree
    var tree = $('#contentCategory')
    tree.tree({
      url: '/manager/content/category/list',
      animate: true,
      lines: true,
      method: 'GET',
      // 当右键点击节点时触发
      onContextMenu: function (e, node) {
        // 调用事件默认行为
        e.preventDefault()
        // 查找节点
        $(this).tree('select', node.target)
        // 显示快捷菜单
        $('#contentCategoryMenu').menu('show', {
          left: e.pageX,
          top: e.pageY
        })
      },
      // 编辑节点后触发
      onAfterEdit: function (node) {
        var _tree = $(this)
        if (node.id === 0) {
          // 新增节点
          $.post('/manager/content/category/create', {parentId: node.parentId, name: node.text}, function (data) {
            if (data.status === 200) {
              _tree.tree('update', {
                target: node.target,
                id: data.data.id
              })
            } else {
              $.messager.alert('提示', '创建' + node.text + ' 分类失败!')
            }
          })
        } else {
          $.post('/manager/content/category/update', {id: node.id, name: node.text})
        }
      }
    })
  })

  function menuHandler(item) {
    var tree = $('#contentCategory')
    var node = tree.tree('getSelected')
    if (item.name === 'add') {
      tree.tree('append', {
        parent: (node ? node.target : null),
        data: [{
          text: '新建分类',
          id: 0,
          parentId: node.id
        }]
      })
      var _node = tree.tree('find', 0)
      tree.tree('select', _node.target).tree('beginEdit', _node.target)
    } else if (item.name === 'edit') {
      tree.tree('beginEdit', node.target)
    } else if (item.name === 'remove') {
      $.messager.confirm('确认', '确定删除名为 ' + node.text + ' 的分类吗？', function (r) {
        if (r) {
          $.post('/manager/content/category/delete', {id: node.id}, function (data) {
            if (data.status === 200) {
              tree.tree('remove', node.target)
            } else {
              $.messager.alert('提示', '不允许删除父节点！', 'warning')
            }
          })
        }
      })
    }
  }
</script>