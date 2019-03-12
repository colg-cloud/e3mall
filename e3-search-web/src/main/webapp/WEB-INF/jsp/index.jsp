<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- read config start -->
<fmt:setBundle basename="conf/server_url" var="server_url"/>
<fmt:message bundle="${server_url}" key="item_url_nginx" var="item_url" />
<!-- read config end -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Cache-Control" content="max-age=300"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>e3 商城 - 商品搜索(${query}}</title>
<link rel="icon" href="/static/icon/favicon.ico">
<link rel="stylesheet" href="/static/css/productList.css"/>
<link rel="stylesheet" href="/static/css/base_w1200.css"/>
<link rel="stylesheet" href="/static/css/common.css"/>
<link rel="stylesheet" href="/static/css/jquery.alerts.css"/>
<script src="/static/js/jquery-1.5.1.min.js"></script>
<script src="/static/js/search.js"></script>
</head>
<body>
  <!-- header start -->
  <jsp:include page="commons/header.jsp"/>
  <jsp:include page="commons/mainmenu.jsp"/>
  <!-------面包线 linknav--------->
  <div class="linknav">
    <div class="schArticle">
      <a href="/article/search?keyword=%E6%9C%88%E9%A5%BC" target="_blank">找到和"<span>${query}</span>"相关的产品<span
          id="articlenum">${totalPages}</span>页&gt;&gt;
      </a>
    </div>
    <div class="breadcrumb">
      <span>全部结果&nbsp;&gt;&nbsp;${query}</span>
    </div>
  </div>
  <div class="content_list">
    <div class="main-box">
      <a id="prolist-id"></a>
      <div class="r-filter">
        <div class="f-sort">
          <div class="pagin">
            <span class="txt"><span class="n">${page}</span>/${totalPages}</span>
            <span class="prev">上一页</span><span class="next">下一页</span></div>
          <div class="total">共<span>${recourdCount}</span>个商品</div>
        </div>
      </div>
      <a name="prolist" id="prolist"></a>
      <div class="p-list">
        <ul class="list-all">
          <c:forEach items="${itemList}" var="item">
            <li>
              <div class="l-wrap">
                <div class="pic">
                  <a class="trackref" href="${item_url}/${item.id}.html" title="" target="_blank">
                    <img src="${item.images[0]}" style="display:inline"/>
                  </a>
                </div>
                <div class="price">
                  <span>
                    <span class="p-now">￥
                      <strong>
                        <fmt:formatNumber groupingUsed="false" maxFractionDigits="2" value="${item.price / 100 }"/>
                      </strong>
                    </span>
                    <span class="p-nor"></span><span class="active" style="">直降</span></span>
                </div>
                <div class="title-a">
                  <a class="trackref presaleSign_225865" href="${item_url}/${item.id}.html" target="_blank">${item.title }</a>
                </div>
                <div class="title-b" style=""><a class="trackref" href="${item_url}/${item.id}.html" target="_blank">${sellPoint }</a></div>
                <div class="comment">
                  <div class="owner_shop_list">自营</div>
                </div>
              </div>
            </li>
          </c:forEach>
        </ul>
        <span class="clear"></span>
      </div>
      <div class="pages"></div>
    </div>
    <div class="left-box">
      <div class="catlist" id="cateall">
        <div class="ct"><h2>在结果中筛选</h2></div>
        <div class="cm">
          <div class="catitem" style="border-top-width: 0px;">
            <h3>
              <b></b>
              <a href="/productlist/search/?categoryId=8&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc" class="">生鲜食品
                <span>（1）</span>
              </a>
            </h3>
            <ul>
              <li><a href="/productlist/search/?categoryId=57&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">蛋品（1）</a></li>
              <li><a href="/productlist/search/?categoryId=292&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">&nbsp;&nbsp;&nbsp;&nbsp;松花蛋/咸鸭蛋（1）</a></li>
            </ul>
          </div>
          <div class="catitem">
            <h3>
              <b></b>
              <a href="/productlist/search/?categoryId=6&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc" class="">休闲食品
                <span>（35）</span>
              </a>
            </h3>
            <ul>
              <li><a href="/productlist/search/?categoryId=44&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">糖果/巧克力（2）</a></li>
              <li><a href="/productlist/search/?categoryId=238&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">&nbsp;&nbsp;&nbsp;&nbsp;巧克力（2）</a></li>
              <li><a href="/productlist/search/?categoryId=7321&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">月饼（33）</a></li>
              <li><a href="/productlist/search/?categoryId=7325&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">&nbsp;&nbsp;&nbsp;&nbsp;冰皮月饼（1）</a></li>
              <li><a href="/productlist/search/?categoryId=7331&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">&nbsp;&nbsp;&nbsp;&nbsp;其他月饼（21）</a></li>
              <li><a href="/productlist/search/?categoryId=7381&amp;keyword=%E6%9C%88%E9%A5%BC&amp;o=saleNum%3Adesc">&nbsp;&nbsp;&nbsp;&nbsp;月饼券（11）</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <jsp:include page="commons/footer.jsp"/>
    <script src="/static/js/common.js?v=20160713"></script>
    <script src="/static/js/cart.js?v=20160713"></script>
    <script src="/static/js/jquery.alerts.js"></script>
    <script src="/static/js/NewVersion.js?v=20160713"></script>
    <script src="/static/js/cookie.js?v=20160416222"></script>
    <script src="/static/js/shadow.js?v=20160416"></script>
  </div>
</html>