<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>地域解读</title>
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/hwes_springmvc/static/css/common.css" />
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
                <form action="wordZone" method="post" id="searchBoxForm">
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
    <script src="/hwes_springmvc/static/js/echarts.js"></script>
    <script src="/hwes_springmvc/static/js/jquery-1.10.2.min.js"></script>
    <script src="/hwes_springmvc/static/js/common.js"></script>
    <script src="/hwes_springmvc/static/conponent/laydate/laydate.js"></script>
    <script type="text/javascript">
        var firstKeyword = "${firstKeyword}";
        var secondKeyword = "${secondKeyword}";
        $("#firstKeyword").attr("value", firstKeyword);
        $("#secondKeyword").attr("value", secondKeyword);
        
        $("#startDate").val("${startDate}");
        $("#endDate").val("${endDate}");
        
        $("#actionURL").attr("value", "wordZone");
        
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
                'echarts/chart/map'
            ],
            function (ec) {
                // --- 地图 ---
                var myChart2 = ec.init(document.getElementById('eChartsMain'));
                if (firstKeyword && secondKeyword) {
                    myChart2.setOption({
                        title : {
                            text: '热词区域解读',
                            x:'center'
                        },
                        tooltip : {
                            trigger: 'item'
                        },
                        legend: {
                            orient: 'vertical',
                            x:'left',
                            data:[firstKeyword,secondKeyword]
                        },
                        dataRange: {
                            min: 0,
                            max: 10000,
                            x: 'left',
                            y: 'bottom',
                            text:['高','低'],           // 文本，默认为数值文本
                            calculable : true
                        },
                        toolbox: {
                            show: true,
                            orient : 'vertical',
                            x: 'right',
                            y: 'center',
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        roamController: {
                            show: true,
                            x: 'right',
                            mapTypeControl: {
                                'china': true
                            }
                        },
                        series : [
                            {
                                name: firstKeyword,
                                type: 'map',
                                mapType: 'china',
                                roam: false,
                                itemStyle:{
                                    normal:{label:{show:true}},
                                    emphasis:{label:{show:true}}
                                },
                                data:[
                                      <%=request.getAttribute("firstWordZoneData") %>
                                      ]
                            },
                            {
                                name: secondKeyword,
                                type: 'map',
                                mapType: 'china',
                                itemStyle:{
                                    normal:{label:{show:true}},
                                    emphasis:{label:{show:true}}
                                },
                                data:[
                                      <%=request.getAttribute("secondWordZoneData") %>
                                      ]
                            }
                        ]
                    });
                } else if (firstKeyword) {
                    myChart2.setOption({
                        title : {
                            text: '热词区域解读',
                            x:'center'
                        },
                        tooltip : {
                            trigger: 'item'
                        },
                        legend: {
                            orient: 'vertical',
                            x:'left',
                            data:[firstKeyword]
                        },
                        dataRange: {
                            min: 0,
                            max: 5000,
                            x: 'left',
                            y: 'bottom',
                            text:['高','低'],           // 文本，默认为数值文本
                            calculable : true
                        },
                        toolbox: {
                            show: true,
                            orient : 'vertical',
                            x: 'right',
                            y: 'center',
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        roamController: {
                            show: true,
                            x: 'right',
                            mapTypeControl: {
                                'china': true
                            }
                        },
                        series : [
                            {
                                name: firstKeyword,
                                type: 'map',
                                mapType: 'china',
                                roam: false,
                                itemStyle:{
                                    normal:{label:{show:true}},
                                    emphasis:{label:{show:true}}
                                },
                                data:[
                                      <%=request.getAttribute("firstWordZoneData") %>
                                      ]
                            }
                        ]
                    });
                }
            }
        );
    </script>
</html>