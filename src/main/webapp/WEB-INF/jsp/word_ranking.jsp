<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>热词排行</title>
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/word_ranking.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/paging.css" />
</head>
    <body>
        <%@ include file="header.jsp" %>
        <div class="navBorder">
            <div class="nav">
                <ul>
                    <li><a href="wordRanking">热词排行</a></li>
                    <li><a href="wordTrend">热词趋势</a></li>
                    <li><a href="wordZone">地域解读</a></li>
                    <li><a href="wordProperty">属性分析</a></li>
                </ul>
            </div>
        </div>
        <div class="mainContent">
            <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
            <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
            <div id="eChartsMain" style="width: 750px;float: right;"></div>
            <table>
                <thead>
                    <tr>
                        <th>排名</th>
                        <th>热词</th>
                        <th>搜索量</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="wordCount" varStatus="num" items="${wordRankingList}">
                        <tr>
                            <td>${(pageBean.currentPage - 1) * 10 + num.index + 1}</td>
                            <td><a href="wordTrend?searchKeyword=${wordCount.keyword}">${wordCount.keyword}</a></td>
                            <td>${wordCount.totalCount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div id="pageTool"></div>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
    
    <script src="/hwes_springmvc/static/js/jquery-1.10.2.min.js"></script>
    <script src="/hwes_springmvc/static/js/common.js"></script>
    <script src="/hwes_springmvc/static/js/query.js"></script>
    <script src="/hwes_springmvc/static/js/paging.js"></script>
    <script type="text/javascript">
        $("#firstKeyword").attr("value", "${firstKeyword}");
        $("#secondKeyword").attr("value", "${secondKeyword}");
        $("#actionURL").attr("value", "wordRanking");
        
        $('#pageTool').Paging({current:"${pageBean.currentPage}" - 0,pagesize:10,count:"${pageBean.pageCount}",callback:function(currentPageRtn, pageSizeRtn, pageCountRtn){
            location.href = "wordRanking?currentPage=" + currentPageRtn;
        }});
    </script>
    <!--Step:2 Import echarts.js-->
    <!--Step:2 引入echarts.js-->
    <script src="/hwes_springmvc/static/js/echarts.js"></script>
    <script type="text/javascript">
        function createRandomItemStyle() {
            return {
                normal: {
                    color: 'rgb(' + [
                        Math.round(Math.random() * 160),
                        Math.round(Math.random() * 160),
                        Math.round(Math.random() * 160)
                    ].join(',') + ')'
                }
            };
        }
        
        // Step:3 conifg ECharts's path, link to echarts.js from current page.
        // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
        require.config({
            paths: {
                echarts: '/hwes_springmvc/static/js'
            }
        });
        
        // Step:4 require echarts and use it in the callback.
        // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
        require(
            [
                'echarts',
                'echarts/chart/wordCloud'
            ],
            function (ec) {
                //--- 折柱 ---
                var myChart = ec.init(document.getElementById('eChartsMain'));
                    myChart.setOption({
                        title: {
                            text: '搜狗热词',
                            link: 'https://www.sogou.com/'
                        },
                        tooltip: {
                            show: true
                        },
                        series: [{
                            name: '热词云',
                            type: 'wordCloud',
                            size: ['80%', '80%'],
                            textRotation : [0, 45, 90, -45],
                            textPadding: 0,
                            autoSize: {
                                enable: true,
                                minSize: 14
                            },
                            data: [
                                <%=request.getAttribute("wordRankingData") %>
                            ]
                        }]
                    });
            }
        );
        
    </script>
</html>