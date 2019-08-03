<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div>
  <ul id="contentCategory"></ul>
</div>

<div id="contentCategoryMenu" class="easyui-menu" style="width: 120px;">
  <div data-options="iconCls:'icon-add', name:'add'">添加</div>
  <div data-options="iconCls:'icon-edit', name:'edit'">重命名</div>
  <div class="menu-sep"></div>
  <div data-options="iconCls:'icon-remove', name:'remove'">删除</div>
</div>

<script>
  $('#contentCategory').tree({
    url: 'content/category/list',
    animate: true,
    lines: true,
    // 当右键点击节点时触发
    onContextMenu: function (e, node) {
      const {pageX: left, pageY: top} = e
      const {target} = node
      // 调用事件默认行为
      e.preventDefault()
      // 选中一个节点，target 参数表示节点的 DOM 对象
      $(this).tree('select', target)
      // 在指定的位置显示菜单
      $('#contentCategoryMenu').menu('show', {left, top})
      $('#contentCategoryMenu').menu({
        onClick: item => {
          let $tree = $('#contentCategory')
          let node = $tree.tree('getSelected')
          if (!node) {
            $.messager.alert('提示', '没有选中节点', 'warning')
          } else {
            const {name} = item
            const {target, id, text} = node
            if (name === 'add') {
              // 追加一些子节点到一个父节点
              $tree.tree('append', {
                parent: target,
                data: [{
                  id: 0,
                  text: '新建分类',
                  parentId: id
                }]
              })
              // 开始编辑节点
              let _node = $tree.tree('find', 0)
              $tree.tree('select', _node.target).tree('beginEdit', _node.target)
            } else if (name === 'edit') {
              $tree.tree('beginEdit', target)
            } else if (name === 'remove') {
              $.messager.confirm('提示', '确定删除名为 ' + text + ' 的分类吗?', r => {
                if (r) {
                  $.post('content/category/delete', {id}, data => {
                    if (data.status === 200) {
                      // 移除一个节点和它的子节点
                      $tree.tree('remove', target)
                    } else {
                      $.messager.alert('提示', '不允许删除父节点', 'warning')
                    }
                  })
                }
              })
            }
          }
        }
      })
    },
    // 编辑节点后触发
    onAfterEdit: function (node) {
      const {target, id, parentId, text: name} = node
      let $tree = $(this)
      if (!name) {
        $.messager.alert('提示', '节点名称为空', 'warning')
      } else {
        if (id === 0) {
          // 新增节点
          $.post('content/category/create', {parentId, name}, data => {
            if (data.status === 200) {
              // 更新指定的节点
              $tree.tree('update', {target, id: data.data.id})
            } else {
              $.messager.alert('提示', '创建' + name + ' 分类失败', 'warning')
            }
          })
        } else {
          $.post('content/category/update', {id, name}, data => {
            if (data.status === 200) {
              $tree.tree('update', {target, id})
            } else {
              $.messager.alert('提示', '修改' + name + ' 分类失败', 'warning')
            }
          })
        }
      }
    }
  })


</script>