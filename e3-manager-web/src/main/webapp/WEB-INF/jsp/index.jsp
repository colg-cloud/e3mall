<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e3 商城 - 后台管理系统(首页)</title>
<%@include file="common/head.jsp" %>
<style>
  .content {
    padding: 10px 10px 10px 10px;
  }
</style>
</head>
<body class="easyui-layout">
  <!--头部标题-->
  <div style="height: 60px; padding: 5px; background: #F3F3F3;"
       data-options="region:'north', border:false">
    <span class="northTitle">e3 商城后台管理系统</span>
    <span class="loginInfo">登录用户: admin&nbsp;&nbsp;姓名: 管理员&nbsp;&nbsp;角色: 系统管理员</span>
  </div>

  <div style="width: 180px;"
       data-options="region:'west', title:'系统菜单', split:true">
    <ul id="menu" class="easyui-tree" style="margin-top: 10px; margin-left: 5px;">
      <li>
        <span>商品管理</span>
        <ul>
          <li data-options="attributes:{'url':'item-add'}">新增商品</li>
          <li data-options="attributes:{'url':'item-list'}">商品列表</li>
          <li data-options="attributes:{'url':'item-param-list'}">规格模版管理</li>
        </ul>
      </li>
      <li>
        <span>网站内容管理</span>
        <ul>
          <li data-options="attributes:{'url':'content-category-tree'}">内容分类管理</li>
          <li data-options="attributes:{'url':'content-list'}">内容管理</li>
        </ul>
      </li>
      <li>
        <span>索引库管理</span>
        <ul>
          <li data-options="attributes:{'url':'solr-index-item'}">solr索引库维护</li>
        </ul>
      </li>
    </ul>
  </div>

  <div data-options="region:'center'">
    <div id="tabs" class="easyui-tabs">
      <div title="首页" style="padding: 20px;">
        <img src="${pageContext.request.contextPath}/static/images/welcome.gif" alt="" style="width: 145px; height: 46px;">
      </div>
    </div>
  </div>

  <!--页脚信息-->
  <div data-options="region: 'south', border: false" style="height: 20px; background: #F3F3F3; padding: 2px; vertical-align: middle;">
    <span id="sysVersion">系统版本: V1.0</span>
    <span id="nowTime"></span>
  </div>

  <script>
    $(() => {
      $('#menu').tree({
        onClick: node => {
          console.log(node)
          if ($('#menu').tree('isLeaf', node.target)) {
            let $tabs = $('#tabs')
            let tab = $tabs.tabs('getTab', node.text)
            if (tab) {
              $tabs.tabs('select', node.text)
            } else {
              $tabs.tabs('add', {
                title: node.text,
                href: node.attributes.url,
                closable: true,
                bodyCls: 'content'
              })
            }
          }
        }
      })
    })


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