String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

String.prototype.ltrim = function() {
    return this.replace(/(^\s*)/g, "");
};

String.prototype.rtrim = function() {
    return this.replace(/(\s*$)/g, "");
};

function submitSelector(obj) {
    var startDate = $("#startDate").val();
    var start=new Date(startDate.replace("-", "/").replace("-", "/"));
    var endDate = $("#endDate").val();
    var end=new Date(endDate.replace("-", "/").replace("-", "/"));
    
    if (!$("#firstKeyword").val().trim() && $("#secondKeyword").val().trim()) {
        $("#firstKeyword").val($("#secondKeyword").val().trim());
        $("#secondKeyword").val("");
    } else if (!$("#firstKeyword").val().trim()
            && !$("#secondKeyword").val().trim() && (obj.id == "searchButton")) {
        $("#innerPop").html("搜索关键词不能为空！");
        showWin();
        return;
    } else if (start > end && (obj.id == "searchButton")) {
        $("#innerPop").html("开始日期必须在结束日期之前!");
        showWin();
        return;
    } else if ((obj.id == "searchButton") && (start < new Date("2006/08/01") || end > new Date("2006/08/31"))) {
        $("#innerPop").html("有效日期区间：2006-08-01 ~ 2006-08-31");
        showWin();
        return;
    }
    
    $("#searchBoxForm").attr("action", $("#actionURL").val());
    $("#searchBoxForm").submit();
}

$(".navSelector").click(function() {
    $("#actionURL").attr("value", this.id);
    $(this).find('a').css("style", "text-decoration:underline");
    submitSelector(this);
});

function showWin() {
    $("#screenMark").show();
    $("#popWin").show();
}

function cancelPop() {
    $("#screenMark").hide();
    $("#popWin").hide();
}