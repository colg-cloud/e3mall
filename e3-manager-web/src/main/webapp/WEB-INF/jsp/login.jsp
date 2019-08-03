<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>e3 商城 - 后台管理系统(登录) </title>
<%@include file="common/head.jsp" %>
<style>
  input {
    width: 200px;
    height: 32px;
  }
</style>
</head>
<body style="background-color: #F3F3F3">
  <div class="easyui-dialog" style="padding: 10px; width: 400px; height: 300px;"
       data-options="title:'管理员登录', closable:false, draggable:false">
    <div style="margin-left: 50px; margin-top: 50px;">
      <div style="margin-bottom: 20px;">
        <div>
          用户名:
          <input class="easyui-textbox" name="username" value="admin"
                 data-options="required:true, iconCls:'icon-man', iconWidth:38"/>
        </div>
      </div>
      <div style="margin-bottom:20px">
        <div>
          密&nbsp;&nbsp;码:
          <input class="easyui-passwordbox" name="password" value="123456"
                 data-options="required:true, iconWidth:38"/>
        </div>
      </div>
      <div>
        <a href="javascript:" class="easyui-linkbutton" style="padding: 5px; width: 80%; font-size: 14px;" onclick="submit()"
           data-options="iconCls:'icon-ok'">登录
        </a>
      </div>
    </div>
  </div>

  <script>
    function submit() {
      const username = $('[name="username"]').val()
      const password = $('[name="password"]').val()

      if (username !== 'admin' || password !== '123456') {
        $.messager.alert('提示', '用户名或密码不正确', 'warning')
      } else {
        window.location.href = 'index'
      }
    }
  </script>
</body>
</html>