<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>热词趋势</title>
    <link rel="stylesheet" type="text/css" href="static/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="static/css/common.css" />
</head>
    <body>
        <!-- disabled screen and pop win -->
        <div class="disabledScreen" id="screenMark" style="display:none"></div>
        <div class="popWin" id="popWin" style="display:none">
          <div class="close" onclick="cancelPop()"><img src="static/image/BTN_Close_16x16.png"></div>
          <div class="title"><span id="innerPop"></span></div>
          <div class="click" id="confirmWin">
            <div class="clickButton" onclick="cancelPop()"><strong>知道了</strong></div>
          </div>
        </div>
        <div class="navBorder">
            <div class="nav">
                <ul>
                    <li id="wordRanking" class="navSelector"><a>热词排行</a></li>
                    <li id="wordTrend" class="navSelector"><a>热词趋势</a></li>
                    <li id="wordZone" class="navSelector"><a>地域解读</a></li>
                    <li id="wordProperty" class="navSelector"><a>属性分析</a></li>
                </ul>
            </div>
        </div>
        <div class="mainContent">
            <div class="searchBoxFunction">
                <form action="wordTrend" method="post" id="searchBoxForm">
                    <input type="text" id="firstKeyword" name="firstKeyword" placeholder="请输入关键词">
                    <input type="text" id="secondKeyword" name="secondKeyword" placeholder="请输入关键词">
                    <input class="laydate-icon" onclick="laydate()" id="startDate" name="startDate" placeholder="选择起始日期">
                    <input class="laydate-icon" onclick="laydate()" id="endDate" name="endDate" placeholder="选择结束日期">
                    <input type="button" id="searchButton" name="searchButton" onclick="submitSelector(this)" value="查询">
                    <input type="text" id="actionURL" name="actionURL" hidden="hidden">
                </form>
            </div>
            <!--Step:1 Prepare a dom for ECharts which (must) has size (width & hight)-->
            <!--Step:1 为ECharts准备一个具备大小（宽高）的Dom-->
            <div id="eChartsMain"></div>
        </div>
        <%@ include file="footer.jsp" %>
    </body>
    <!--Step:2 Import echarts.js-->
    <!--Step:2 引入echarts.js-->
    <script src="static/js/echarts.js"></script>
    <script src="static/js/jquery-1.10.2.min.js"></script>
    <script src="static/js/common.js"></script>
    <script src="static/conponent/laydate/laydate.js"></script>
    <script type="text/javascript">
        var xAxisData = "${xAxisData}".split(",");
        var firstWordTrendData = "${firstWordTrendData}".split(",");
        var secondWordTrendData = "${secondWordTrendData}".split(",");
        var firstKeyword = "${firstKeyword}";
        var secondKeyword = "${secondKeyword}";
        $("#firstKeyword").attr("value", firstKeyword);
        $("#secondKeyword").attr("value", secondKeyword);
        
        $("#startDate").attr("value", "${startDate}");
        $("#endDate").attr("value", "${endDate}");
        
        $("#actionURL").attr("value", "wordTrend");
        
        // Step:3 conifg ECharts's path, link to echarts.js from current page.
        // Step:3 为模块加载器配置echarts的路径，从当前页面链接到echarts.js，定义所需图表路径
        require.config({
            paths: {
                echarts: 'static/js'
            }
        });
        
        // Step:4 require echarts and use it in the callback.
        // Step:4 动态加载echarts然后在回调函数中开始使用，注意保持按需加载结构定义图表路径
        require(
            [
                'echarts',
                'echarts/chart/bar',
                'echarts/chart/line'
            ],
            function (ec) {
                //--- 折柱 ---
                var myChart = ec.init(document.getElementById('eChartsMain'));
                if (firstKeyword && secondKeyword) {
                    myChart.setOption({
                        tooltip : {
                            trigger: 'axis'
                        },
                        legend: {
                            data:[firstKeyword,secondKeyword]
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {show: true, type: ['line', 'bar']},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        xAxis : [
                            {
                                type : 'category',
                                data : xAxisData
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
                                splitArea : {show : true}
                            }
                        ],
                        series : [
                                  {
                                      name:firstKeyword,
                                      type:'bar',
                                      data:firstWordTrendData
                                  },
                                  {
                                      name:secondKeyword,
                                      type:'bar',
                                      data:secondWordTrendData
                                  }
                              ]
                    });
                } else if (firstKeyword) {
                    myChart.setOption({
                        tooltip : {
                            trigger: 'axis'
                        },
                        legend: {
                            data:[firstKeyword]
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {show: true, type: ['line', 'bar']},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        xAxis : [
                            {
                                type : 'category',
                                data : xAxisData
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
                                splitArea : {show : true}
                            }
                        ],
                        series : [
                                  {
                                      name:firstKeyword,
                                      type:'bar',
                                      data:firstWordTrendData
                                  }
                              ]
                    });
                }
            }
        );
    </script>
</html>