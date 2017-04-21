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
    if (!$("#firstKeyword").val().trim() && $("#secondKeyword").val().trim()) {
        $("#firstKeyword").val($("#secondKeyword").val().trim());
        $("#secondKeyword").val("");
    } else if (!$("#firstKeyword").val().trim()
            && !$("#secondKeyword").val().trim() && (obj.id == "searchButton")) {
        $("#innerPop").html("搜索关键词不能为空！");
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