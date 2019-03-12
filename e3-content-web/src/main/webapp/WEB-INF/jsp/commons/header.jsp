<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- read config start -->
<fmt:setBundle basename="conf/server_url" var="server_url"/>
<fmt:message bundle="${server_url}" key="content_url" var="content_url" />
<fmt:message bundle="${server_url}" key="search_url" var="search_url" />
<fmt:message bundle="${server_url}" key="sso_register_url" var="sso_register_url" />
<fmt:message bundle="${server_url}" key="sso_login_url" var="sso_login_url" />
<!-- read config end -->
<!--shortcut start-->
<jsp:include page="shortcut.jsp" />
<!--shortcut end-->
<h3>${content_url}</h3>
<h3>${search_url}</h3>
<h3>${sso_register_url}</h3>
<h3>${sso_login_url}</h3>
<div id="header">
  <div class="header_inner">
    <div class="logo">
      <div class="index_topad" id="playLogo" style="">
        <a href="/html/activity/1472179566.html" target="_blank">
          <img src="/static/images/html/20160829181637762.gif">
        </a>
      </div>
      <a name="sfbest_hp_hp_head_logo" href="${content_url}" class="trackref logoleft">
      </a>
      <div class="logo-text">
        <img src="/static/images/html/logo_word.jpg">
      </div>
    </div>
    <div class="index_promo"></div>
    <div class="search">
      <!-- 跳转到 http://127.0.0.1:9003/search/index.html 搜索 -->
      <form action="${search_url}" id="searchForm" name="query" method="GET">
        <input type="text" class="text keyword ac_input" name="keyword" id="keyword" value="" style="color: rgb(153, 153, 153);"
               onkeydown="javascript:if(event.keyCode==13) search_keys('searchForm');" autocomplete="off">
        <input type="button" value="" class="submit" onclick="search_keys('searchForm')">
      </form>
      <div class="search_hot">
        <c:forEach items="${ad3List}" var="node" varStatus="status">
          <a target="_blank" href="${node.url}?inputBox=1&amp;keyword=${node.title}#trackref=sfbest_hp_hp_head_Keywords${status.index}">${node.title}</a>
        </c:forEach>
        <!--  <a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E5%A4%A7%E9%97%B8%E8%9F%B9#trackref=sfbest_hp_hp_head_Keywords1">大闸蟹</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%9F%B3%E6%A6%B4#trackref=sfbest_hp_hp_head_Keywords2">石榴</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E6%9D%BE%E8%8C%B8#trackref=sfbest_hp_hp_head_Keywords3">松茸</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%89%9B%E6%8E%92#trackref=sfbest_hp_hp_head_Keywords4">牛排</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%99%BD%E8%99%BE#trackref=sfbest_hp_hp_head_Keywords5">白虾</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E5%85%A8%E8%84%82%E7%89%9B%E5%A5%B6#trackref=sfbest_hp_hp_head_Keywords6">全脂牛奶</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E6%B4%8B%E6%B2%B3#trackref=sfbest_hp_hp_head_Keywords7">洋河</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E7%BB%BF%E8%B1%86#trackref=sfbest_hp_hp_head_Keywords8">绿豆</a><a target="_blank" href="http://www.e3mall.cn/productlist/search?inputBox=1&amp;keyword=%E4%B8%80%E5%93%81%E7%8E%89#trackref=sfbest_hp_hp_head_Keywords9">一品玉</a> -->
      </div>
    </div>
    <div class="shopingcar" id="topCart">
      <s class="setCart"></s><a href="http://127.0.0.1:8090/cart/cart.html" class="t" rel="nofollow">我的购物车</a><b id="cartNum">0</b>
      <span class="outline"></span>
      <span class="blank"></span>
      <div id="cart_lists">
        <!--cartContent-->
        <div class="clear"></div>
      </div>
    </div>
  </div>
</div>