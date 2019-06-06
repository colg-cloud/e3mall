<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e3 商城 - 后台管理系统(登录) </title>
<%@include file="common/head.jsp" %>
</head>
<body style="background-color: #F3F3F3">
  <div class="easyui-dialog" title="管理员登录" style="width: 400px; height: 300px; padding: 10px;"
       data-options="closable:false, draggable:false">
    <div style="margin-left: 50px; margin-top: 50px;">
      <div style="margin-bottom: 20px;">
        <div>
          用户名:
          <input class="easyui-textbox" name="username" value="admin" style="width: 200px; height: 32px;"
                 data-options="required:true, iconCls:'icon-man', iconWidth:38"/>
        </div>
      </div>
      <div style="margin-bottom:20px">
        <div>
          密&nbsp;&nbsp;码: &nbsp;
          <input class="easyui-textbox" name="password" value="123456" style="width: 200px; height: 32px;"
                 data-options="required:true, iconCls:'icon-lock', iconWidth:38"/>
        </div>
      </div>
      <div>
        <a id="login" class="easyui-linkbutton" style="padding: 5px 0; width: 80%; font-size: 14px;"
           data-options="iconCls:'icon-ok'">
          登录
        </a>
      </div>
    </div>
  </div>

  <script>
    $('#login').click(() => {
      const username = $('[name=username]').val()
      const password = $('[name=password]').val()

      if (username !== 'admin' || password !== '123456') {
        $.messager.alert('错误', '用户名或密码不正确!', 'warning')
        return
      }
      window.location.href = '/manager/index'
    })
  </script>
</body>
</html>