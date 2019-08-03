<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>e3 商城 - 后台管理系统(首页)</title>
<%@include file="common/head.jsp" %>
<style>
  .content {
    padding: 5px;
  }
</style>
</head>
<body class="easyui-layout">
  <!--头部标题-->
  <div style="padding: 5px; height: 60px; background: #F3F3F3;"
       data-options="region:'north', border:false">
    <span class="northTitle">e3 商城后台管理系统</span>
    <span class="loginInfo">登录用户: admin&nbsp;&nbsp;姓名: 管理员&nbsp;&nbsp;角色: 系统管理员</span>
  </div>

  <div style="width: 180px;"
       data-options="region:'west', title:'系统菜单', split:true">
    <ul id="menu" style="margin-top: 10px; margin-left: 5px;">
      <li>
        <span>商品管理</span>
        <ul>
          <li data-options="attributes:{'url':'item/add'}">新增商品</li>
          <li data-options="attributes:{'url':'item/list'}">商品管理</li>
          <li data-options="attributes:{'url':'item-param/list'}">规格模版管理</li>
        </ul>
      </li>
      <li>
        <span>网站内容管理</span>
        <ul>
          <li data-options="attributes:{'url':'content-category/tree'}">内容分类管理</li>
          <li data-options="attributes:{'url':'content/list'}">内容管理</li>
        </ul>
      </li>
      <li>
        <span>索引库管理</span>
        <ul>
          <li data-options="attributes:{'url':'solr/index-item'}">solr索引库维护</li>
        </ul>
      </li>
    </ul>
  </div>

  <div data-options="region:'center'">
    <div id="tabs" class="easyui-tabs">
      <div style="padding: 20px;"
           data-options="title:'首页'">
        <img src="${pageContext.request.contextPath}/static/images/welcome.jpg" alt="" style="width: 90%; height: 90%;">
      </div>
    </div>
  </div>

  <!--页脚信息-->
  <div style="height: 20px; background: #F3F3F3; padding: 2px; vertical-align: middle;"
       data-options="region:'south', border:false">
    <span id="sysVersion">系统版本: V1.0</span>
    <span id="nowTime"></span>
  </div>

  <script>
    $('#menu').tree({
      onClick: node => {
        console.log(node)
        const {target, text, attributes: {url}} = node
        if ($('#menu').tree('isLeaf', target)) {
          let $tabs = $('#tabs')
          if ($tabs.tabs('getTab', text)) {
            $tabs.tabs('select', text)
          } else {
            $tabs.tabs('add', {
              title: text,
              href: url,
              closable: true,
              bodyCls: 'content'
            })
          }
        }
      }
    })

    // 当前时间
    const week = '日一二三四五六'
    let $nowTime = $('#nowTime')
    let now = new Date()
    $nowTime.html(now.toLocaleString() + ' 星期' + week.charAt(now.getDay()))
    setInterval(() => {
      now = new Date()
      $nowTime.html(now.toLocaleString() + ' 星期' + week.charAt(now.getDay()))
    }, 1000)
  </script>
</body>
</html>