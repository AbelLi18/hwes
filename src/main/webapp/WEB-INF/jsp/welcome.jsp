<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>热词指数</title>
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/welcome.css" />
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
        <div class="mainContent">
            <!-- search box -->
            <div id="searchBox">
                <h1>热词指数</h1><br>
                <form action="wordTrend" method="post" id="searchForm">
                    <input type="text" name="searchKeyword" id="searchKeyword" placeholder="请输入关键词">
                    <input type="button" id="searchButton" value="搜一下" onclick="submitSearch()">
                </form>
            </div>
            
            <div id="functionTab">
                <!-- hot words -->
                <div id="wordRanking" class="homeNav">
                    <h3>大家都在搜</h3>
                    <table id="hotWordTable">
                        <tbody>
                            <c:forEach var="wordCount" varStatus="num" begin="0" step="1" end="4" items="${requestScope.wordCountList}">
                                <tr>
                                    <td id="keyword"><a id="wordRanking" class="homeNav">${wordCount.keyword}</a></td>
                                    <td id="count">${wordCount.totalCount}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <br><br>
                <!-- hot words PK-->
                <div id="wordTrend" class="homeNav">
                    <h3>热词PK</h3>
                </div>
                
                <br><br>
                <!-- zone analysis-->
                <div id="wordZone" class="homeNav">
                    <h3>地域分析</h3>
                </div>
                
                <br><br>
                <!-- property analysis-->
                <div id="wordProperty" class="homeNav">
                    <h3>属性分析</h3>
                </div>
            </div>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
    <script src="/hwes_springmvc/static/js/jquery-1.10.2.min.js"></script>
    <script src="/hwes_springmvc/static/js/common.js"></script>
    <script type="text/javascript">
        function submitSearch() {
            if ($("#searchKeyword").val().trim()) {
                $("#searchForm").submit();
            } else {
                $("#innerPop").html("搜索关键词不能为空！");
                showWin();
            }
        }
        
        $(".homeNav").click(function() {
            $("#searchKeyword").val("${defaultKeyword}");
            $("#searchForm").attr("action", this.id);
            submitSearch();
        });
    </script>
</html>