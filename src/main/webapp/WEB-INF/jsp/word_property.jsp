<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>属性分析</title>
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/word_property.css" />
</head>
    <body>
        <!-- disabled screen and pop win -->
        <div class="disabledScreen" id="screenMark" style="display:none"></div>
        <div class="popWin" id="popWin" style="display:none">
          <div class="close" onclick="cancelPop()"><img src="/hwes_springmvc/static/image/BTN_Close_16x16.png"></div>
          <div class="title"><span id="innerPop"></span></div>
          <div class="click" id="confirmWin">
            <div class="clickButton" onclick="cancelPop()"><strong>知道了</strong></div>
          </div>
        </div>
        <%@ include file="header.jsp" %>
        <div class="navBorder">
            <div class="nav">
                <ul>
                    <li><a id="wordRanking" class="navSelector">热词排行</a></li>
                    <li><a id="wordTrend" class="navSelector">热词趋势</a></li>
                    <li><a id="wordZone" class="navSelector">地域解读</a></li>
                    <li><a id="wordProperty" class="navSelector">属性分析</a></li>
                </ul>
            </div>
        </div>
        <div class="mainContent">
            <div class="searchBoxFunction">
                <form action="wordProperty" method="post" id="searchBoxForm">
                    <input type="text" id="firstKeyword" name="firstKeyword" placeholder="请输入关键词">
                    <input type="text" id="secondKeyword" name="secondKeyword" placeholder="请输入关键词">
                    <input class="laydate-icon" onclick="laydate()" id="startDate" name="startDate" placeholder="选择开始日期" disabled="disabled">
                    <input class="laydate-icon" onclick="laydate()" id="endDate" name="endDate" placeholder="选择结束日期" disabled="disabled">
                    <input type="button" id="searchButton" name="searchButton" onclick="submitSelector(this)" value="查询">
                    <input type="text" id="actionURL" name="actionURL" hidden="hidden">
                </form>
            </div>
            <div>
                <div id="wordHotSearch"></div>
            </div>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
    <script src="/hwes_springmvc/static/js/jquery-1.10.2.min.js"></script>
    <script src="/hwes_springmvc/static/js/common.js"></script>
    <script src="/hwes_springmvc/static/conponent/laydate/laydate.js"></script>
    <script type="text/javascript">
        var firstKeyword = "${firstKeyword}";
        var secondKeyword = "${secondKeyword}";
        $("#firstKeyword").attr("value", firstKeyword);
        $("#secondKeyword").attr("value", secondKeyword);
        $("#actionURL").attr("value", "wordProperty");
        
        
        var html = '<div class="wordContext"><label>没有搜索到相关热词！</label></div>';
        if (firstKeyword && secondKeyword) {
            html = '<div class="wordContext"><label>相关搜索 : </label><span>${firstKeyword}</span><br><br>' +
                   '<c:forEach var="wordContext" varStatus="num" items="${requestScope.firstWordContext}">' +
                       '<a href="https://www.sogou.com/web?query=${wordContext}">${wordContext}</a><br>' +
                   '</c:forEach>' +
                   '<div class="secondWordContext"><label>相关搜索 : </label><span>${secondKeyword}</span><br><br>' +
                   '<c:forEach var="wordContext" varStatus="num" items="${requestScope.secondWordContext}">' +
                       '<a href="https://www.sogou.com/web?query=${wordContext}">${wordContext}</a><br>' +
                   '</c:forEach></div></div>';
        } else if (firstKeyword) {
            html = '<div class="wordContext"><label>相关搜索 : </label><span>${firstKeyword}</span><br><br>' +
                   '<c:forEach var="wordContext" varStatus="num" items="${requestScope.firstWordContext}">' +
                       '<a href="https://www.sogou.com/web?query=${wordContext}">${wordContext}</a><br>' +
                   '</c:forEach></div>';
        }
        $("#wordHotSearch").html(html);
    </script>
</html>