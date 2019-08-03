<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemParamAddTable" class="itemParam">
  <tr>
    <td>商品类目:</td>
    <td>
      <a href="javascript:" class="easyui-linkbutton selectItemCat">选择类目</a>
      <input type="hidden" name="cid"/>
    </td>
  </tr>
  <tr class="hide addGroupTr">
    <td>规格参数:</td>
    <td>
      <ul>
        <li>
          <a href="javascript:" class="easyui-linkbutton addGroup" style="font-weight:bold;"
             data-options="plain:true, iconCls:'icon-add'">添加分组
          </a>
        </li>
      </ul>
    </td>
  </tr>
  <tr>
    <td></td>
    <td>
      <a href="javascript:" class="easyui-linkbutton submit"
         data-options="iconCls:'icon-ok'">提交
      </a>
      <a href="javascript:" class="easyui-linkbutton close"
         data-options="iconCls:'icon-no'">关闭
      </a>
    </td>
  </tr>
</table>

<div class="itemParamAddTemplate" style="display: none;">
  <li class="param" style="margin-top: 10px;">
    <ul>
      <li>
        <input class="easyui-textbox" name="group" style="width: 300px;"/>
        <a href="javascript:" class="easyui-linkbutton addParam" style="margin-left: 10px;"
           data-options="text:'添加参数', plain:true, iconCls:'icon-add'">
        </a>
      </li>
      <li style="margin-top: 2px;">
        <span>|-------</span>
        <input class="easyui-textbox" name="param" style="width: 300px;"/>
        <a href="javascript:" class="easyui-linkbutton delParam"
           data-options="text:'删除', plain:true, iconCls:'icon-remove'">
        </a>
      </li>
    </ul>
  </li>
</div>

<script>
  $(() => {
    E3.initItemCat({
      fun: node => {
        $('.addGroupTr').hide().find('.param').remove()
        const {id} = node
        //  判断选择的目录是否已经添加过规格
        $.post('item/param/select/' + id, data => {
          if (data.status === 200 && data.data) {
            $.messager.alert('提示', '该类目已经添加过规格参数, 请选择其他类目', 'warning', () => {
              $('#itemParamAddTable .selectItemCat').click()
            })
          } else {
            $('.addGroupTr').show()
          }
        })
      }
    })

    $('.addGroup').click(function () {
      let temple = $('.itemParamAddTemplate li').eq(0).clone()
      $(this).parent().parent().append(temple)
      temple.find('.addParam').click(function () {
        let li = $('.itemParamAddTemplate li').eq(2).clone()
        li.find('.delParam').click(function () {
          $(this).parent().remove()
        })
        li.appendTo($(this).parentsUntil('ul').parent())
      })
      temple.find('.delParam').click(function () {
        $(this).parent().remove()
      })
    })

    $('#itemParamAddTable .submit').click(() => {
      let groups = $('#itemParamAddTable [name=group]')
      console.log(groups)
      let params = []
      groups.each((i, e) => {
        let p = $(e).parentsUntil('ul').parent().find('[name=param]')
        let _ps = []
        p.each((_i, _e) => {
          let _val = $(_e).siblings('input').val()
          if ($.trim(_val).length > 0) {
            _ps.push(_val)
          }
        })
        let _val = $(e).siblings('input').val()
        if ($.trim(_val).length > 0 && _ps.length > 0) {
          params.push({
            'group': _val,
            'params': _ps
          })
        }
      })
      $.post('item/param/save/' + $('#itemParamAddTable [name=cid]').val(), {'paramData': JSON.stringify(params)}, data => {
        if (data.status === 200) {
          $.messager.alert('提示', '新增商品规格成功!', 'info', () => {
            E3.closeCurrentWindow()
            $('#itemParamList').datagrid('reload')
          })
        }
      })
    })

    $('#itemParamAddTable .close').click(() => {
      E3.closeCurrentWindow()
    })
  })
</script>