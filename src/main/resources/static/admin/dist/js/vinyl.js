$(function () {
    $("#jqGrid").jqGrid({
        url: '/vinyl/list',
        mtype: "POST",
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '单曲名称', name: 'singleName', index: 'singleName', width: 140},
            {label: '唱片标题', name: 'vinylTitle', index: 'vinylTitle', width: 140},
            {label: '唱片封面', name: 'vinylCover', index: 'vinylCover', width: 120, formatter: coverImageFormatter},
            {label: '艺术家', name: 'artist', index: 'artist', width: 100},
            {label: '专辑名称', name: 'albumName', index: 'albumName', width: 60},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 90},
            {label: '详情', name: 'detail', index: 'detail', width: 50, formatter: detailButtonFormatter} // 增加详情按钮列
        ],
        height: 700,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
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
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        },
        ajaxGridOptions: {
            contentType: "application/json"
        },
        serializeGridData: function (postData) {
            console.log("Serialized postData:", postData); // 添加日志
            return JSON.stringify(postData);
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"120\" width=\"160\" alt='coverImage'/>";
    }

    // 新增详情按钮格式化函数
    function detailButtonFormatter(cellvalue, options, rowObject) {
        return "<button class='btn btn-info' onclick='viewDetail(" + rowObject.id + ")'><i class='fa fa-info-circle'></i>&nbsp;详情</button>";
    }

    // 获取专辑数据并填充到下拉框
    $.ajax({
        url: '/album/select',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var select = $('#keywordAlbumName');
            data.data.forEach(function (album) {
                select.append($('<option>', {
                    value: album.id,
                    text: album.albumName
                }));
            });
        },
        error: function (xhr, status, error) {
            console.error('Error fetching album data:', error);
        }
    });

});

/**
 * 搜索功能
 */
function searchVinyl() {
    // 获取搜索关键字
    var singleName = $('#keywordSingleName').val();
    var vinylTitle = $('#keywordVinylTitle').val();
    var artist = $('#keywordArtist').val();
    var albumId = $('#keywordAlbumName').val(); // 获取选中的专辑ID

    // 数据封装
    var searchData = {
        singleName: singleName,
        vinylTitle: vinylTitle,
        artist: artist,
        albumId: albumId // 使用专辑ID
    };

    // 传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    // 点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    // 提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/vinyl/list'}).trigger("reloadGrid");
}

/**
 * jqGrid重新加载
 */
function reloadVinyl() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function addVinyl() {
    window.location.href = "/page/vinyl/edit";
}

function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.jqGrid('getGridParam', 'selrow');
    if (rowKey) {
        return parseInt(grid.jqGrid('getCell', rowKey, 'id'), 10);
    }
    return null;
}

function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKeys = grid.jqGrid('getGridParam', 'selarrrow');
    if (rowKeys && rowKeys.length > 0) {
        return rowKeys.map(function(rowKey) {
            return parseInt(grid.jqGrid('getCell', rowKey, 'id'), 10);
        });
    }
    return null;
}

function editVinyl() {
    var id = getSelectedRow();
    console.log("Editing vinyl with id:", id); // 添加日志
    if (id == null || isNaN(id)) {
        swal("提示", "请选择要编辑的记录", "warning");
        return;
    }
    window.location.href = "/page/vinyl/edit/" + id; // 修正URL拼接方式
}

function deleteVinyl() {
    var ids = getSelectedRows();
    if (ids == null || ids.length === 0) {
        swal("提示", "请选择要删除的记录", "warning");
        return;
    }
    console.log("Deleting vinyls with ids:", ids); // 添加日志
    if (ids.some(isNaN)) {
        swal("提示", "选中的记录包含无效ID", "warning");
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/vinyl/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

function clearInput(id) {
    $('#' + id).val('');
}

function resetSearch() {
    clearInput('keywordSingleName');
    clearInput('keywordVinylTitle');
    clearInput('keywordArtist');
    $('#keywordAlbumName').val(''); // 重置专辑名称下拉框
    searchVinyl(); // 重新加载表格数据
}

// 新增查看详情函数
function viewDetail(id) {
    window.location.href = "/page/vinyl/detail/" + id;
}