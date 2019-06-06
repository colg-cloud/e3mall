<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<table id="itemParamAddTable" class="itemParam" style="margin-left: 30px; padding: 5px">
  <tr>
    <td>商品类目:</td>
    <td>
      <a href="javascript:" class="easyui-linkbutton selectItemCat">选择类目</a>
      <input type="hidden" name="cid" style="width: 280px;"/>
    </td>
  </tr>
  <tr class="hide addGroupTr">
    <td>规格参数:</td>
    <td>
      <ul>
        <li><a href="javascript:" class="easyui-linkbutton addGroup">添加分组</a></li>
      </ul>
    </td>
  </tr>
  <tr>
    <td></td>
    <td>
      <a href="javascript:" class="easyui-linkbutton submit">提交</a>
      <a href="javascript:" class="easyui-linkbutton close">关闭</a>
    </td>
  </tr>
</table>

<div class="itemParamAddTemplate" style="display: none;">
  <li class="param">
    <ul>
      <li>
        <input class="easyui-textbox" name="group" style="width: 150px;"/>
        <a href="javascript:" class="easyui-linkbutton addParam"
           data-options="title:'添加参数', plain:true, iconCls:'icon-add'">
        </a>
      </li>
      <li>
        <span>|-------</span>
        <input class="easyui-textbox" name="param" style="width: 150px;"/>
        <a href="javascript:" class="easyui-linkbutton delParam"
           data-options="title:'删除', plain:true, iconCls:'icon-cancel'">
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
        $.getJSON('/manager/item/param/select/' + id, data => {
          if (data.status === 200 && data.data) {
            $.messager.alert('提示', '该类目已经添加, 请选择其他类目!', 'warning', () => {
              $('#itemParamAddTable .selectItemCat').click()
            })
            return
          }
          $('.addGroupTr').show()
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
      $.post('/manager/item/param/save/' + $('#itemParamAddTable [name=cid]').val(), {'paramData': JSON.stringify(params)}, data => {
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