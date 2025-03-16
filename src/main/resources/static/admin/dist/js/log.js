$(function () {
    $("#logGrid").jqGrid({
        url: '/admin/log/list',
        mtype: "POST",
        datatype: "json",
        colModel: [
            {label: '日志主键', name: 'operId', index: 'operId', key: true, hidden: true},
            {label: '模块', name: 'title', index: 'title', width: 110},
            {label: '业务', name: 'businessType', index: 'businessType', width: 80, formatter: formatBusinessType},
            {label: '方法', name: 'method', index: 'method', width: 80,formatter: formatLogDetail},
            {label: '请求方式', name: 'requestMethod', index: 'requestMethod', width: 110},
            {label: '操作人员', name: 'operName', index: 'operName', width: 110},
            {label: '请求URL', name: 'operUrl', index: 'operUrl', width: 110,formatter: formatLogDetail},
            {label: '主机地址', name: 'operIp', index: 'operIp', width: 110},
            {label: '请求参数', name: 'operParam', index: 'operParam', width: 150, formatter: formatLogDetail},
            {label: '返回参数', name: 'jsonResult', index: 'jsonResult', width: 150, formatter: formatLogDetail},
            {label: '操作状态', name: 'status', index: 'status', width: 110, formatter: formatStatus},
            {label: '错误消息', name: 'errorMsg', index: 'errorMsg', width: 110, formatter: formatLogDetail},
            {label: '操作时间', name: 'operTime', index: 'operTime', width: 150},
            {label: '消耗时间', name: 'costTime', index: 'costTime', width: 110},
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: false,
        pager: "#logGridPager",
        jsonReader: {
            root: "rows",
            page: "current",
            total: "pages",
            records: "total"
        },
        prmNames: {
            page: "pageNum",
            rows: "pageSize",
            order: "isAsc",
            sort: "orderByColumn"
        },
        gridComplete: function () {
            $('[data-toggle="tooltip"]').tooltip();
        },
        ajaxGridOptions: {
            contentType: "application/json"
        },
        serializeGridData: function (postData) {
            return JSON.stringify(postData);
        }
    });

    $(window).resize(function () {
        $("#logGrid").setGridWidth($(".card-body").width());
    });

    $('#clearSearchKeyword').click(function () {
        $('#searchKeyword').val('');
    });
});

function formatBusinessType(cellvalue) {
    return {0: '其它', 1: '新增', 2: '修改', 3: '删除'}[cellvalue] || '未知';
}

function formatStatus(cellvalue) {
    return cellvalue === 0 ? '正常' : '异常';
}

function formatLogDetail(cellvalue, options, rowObject) {
    if (!cellvalue) return '';

    var id = options.rowId + '-' + options.colModel.name;
    return `<span data-toggle="modal" data-target="#logDetailModal" onclick="showLogDetail('${escapeHtml(cellvalue)}')">
                ${cellvalue.length > 20 ? cellvalue.substring(0, 20) + '...' : cellvalue}
            </span>`;
}

function showLogDetail(content) {
    $("#logDetailContent").text(content);
    $("#logDetailModal").modal("show");
}

function escapeHtml(text) {
    return text.replace(/[&<>"']/g, function (match) {
        return {'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'}[match];
    });
}

function searchLogs() {
    $("#logGrid").jqGrid('setGridParam', {
        postData: {keyword: $("#searchKeyword").val()}
    }).trigger("reloadGrid");
}

function resetSearchForm() {
    $('#searchKeyword').val('');
    searchLogs();
}

function clearLogs() {
    swal({
        title: "确认清空日志?",
        text: "此操作不可恢复!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.post("/admin/log/clean", function (r) {
                r.code === 200 ? swal("清空成功", {icon: "success"}) : swal(r.msg, {icon: "error"});
                $("#logGrid").trigger("reloadGrid");
            });
        }
    });
}
