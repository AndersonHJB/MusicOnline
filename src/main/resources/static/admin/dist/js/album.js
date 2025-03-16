$(function () {
    $("#jqGrid").jqGrid({
        url: '/album/list',
        mtype: "POST",
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '专辑名称', name: 'albumName', index: 'albumName', width: 240},
            {label: '专辑封面', name: 'albumCover', index: 'albumCover', width: 120, formatter: imgFormatter},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 120}
        ],
        height: 560,
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
        }
        ,
        ajaxGridOptions: {
            contentType: "application/json"
        },
        serializeGridData: function (postData) {
            console.log("Serialized postData:", postData); // 添加日志
            return JSON.stringify(postData);
        }
    });

    jQuery("select.image-picker").imagepicker({
        hide_select: false,
    });

    jQuery("select.image-picker.show-labels").imagepicker({
        hide_select: false,
        show_label: true,
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    var container = jQuery("select.image-picker.masonry").next("ul.thumbnails");
    container.imagesLoaded(function () {
        container.masonry({
            itemSelector: "li",
        });
    });

    // 获取 token 的方法
    function getTokenFromSession() {
        // 这里假设 token 存储在 localStorage 中
        var token = localStorage.getItem('token');
        console.log("获取到的 token：" + token);
        return token;
    }

    $('#uploadCoverImage').click(function (event) {
        event.preventDefault(); // 阻止默认行为，防止表单提交或事件冒泡导致模态框关闭
        var fileInput = document.createElement('input');
        fileInput.type = 'file';
        fileInput.accept = 'image/jpg,image/jpeg,image/png,image/gif';
        fileInput.onchange = function () {
            var file = fileInput.files[0];
            if (file) {
                var validExtensions = ['jpg', 'jpeg', 'png', 'gif'];
                var extension = file.name.split('.').pop().toLowerCase();
                if (validExtensions.indexOf(extension) === -1) {
                    alert('只支持jpg、png、gif格式的文件！');
                    return;
                }

                var formData = new FormData();
                formData.append('file', file);

                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/common/upload/file', true);
                xhr.setRequestHeader('Authorization', getTokenFromSession());
                xhr.responseType = 'json';

                xhr.onload = function () {
                    if (xhr.status === 200) {
                        var r = xhr.response;
                        if (r != null && r.code === 200) {
                            console.log("上传成功，图片URL:", r.data); // 添加调试信息
                            $("#albumCover").attr("src", r.data); // 修改选择器为 #albumCover
                        } else {
                            alert("上传失败");
                        }
                    } else {
                        alert("上传失败");
                    }
                };

                xhr.send(formData);
            }
        };
        fileInput.click();
    });

    $('#clearCoverImage').click(function (event) {
        event.preventDefault(); // 阻止默认行为，防止表单提交导致页面刷新
        $('#albumCover').attr('src', '/admin/dist/img/album/00.png'); // 修改选择器为 #albumCover
    });

    $('#albumModal').on('hidden.bs.modal', function () {
        $('#albumCover').attr('src', '/admin/dist/img/album/00.png'); // 修改选择器为 #albumCover
    });
});

function imgFormatter(cellvalue) {
    // 修改渲染方式以支持上传的图片 URL 或 base64 数据
    return "<a href='" + cellvalue + "'> <img src='" + cellvalue + "' height=\"64\" width=\"64\" alt='icon'/></a>";
}

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function albumAdd() {
    reset();
    $('.modal-title').html('专辑添加');
    $('#albumModal').modal('show'); // 修改模态框ID
}

// 绑定modal上的保存按钮
$('#saveButton').click(function () {
    var albumName = $("#albumName").val();
    if (!validCN_ENString2_18(albumName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的专辑名称！");
    } else {
        var albumCover = $("#albumCover").attr("src"); // 获取封面地址
        var params = {
            albumName: albumName,
            albumCover: albumCover
        }; // 修改参数为 JSON 格式
        var url = '/album/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            params.id = id; // 如果有 id，则添加到参数中
            url = '/album/update';
        }
        $.ajax({
            type: 'POST', // 方法类型
            url: url,
            contentType: 'application/json', // 设置内容类型为 JSON
            data: JSON.stringify(params), // 将参数转换为 JSON 字符串
            success: function (result) {
                if (result.code === 200) {
                    $('#albumModal').modal('hide'); // 修改模态框ID
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#albumModal').modal('hide'); // 修改模态框ID
                    swal(result.msg, {
                        icon: "error",
                    });
                }
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    }
});

function albumEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    $('.modal-title').html('专辑编辑');
    $('#albumModal').modal('show'); // 修改模态框ID
    $("#albumId").val(id);

    // 添加 AJAX 请求以获取专辑详细信息
    $.ajax({
        type: "GET",
        url: "/album/info/" + id,
        contentType: "application/json",
        success: function (result) {
            if (result.code === 200) {
                var album = result.data;
                $("#id").val(album.id);
                $("#albumName").val(album.albumName); // 填充专辑名称
                $("#albumCover").attr("src", album.albumCover); // 填充专辑封面
                
            } else {
                swal(result.msg, {
                    icon: "error",
                });
            }
        },
        error: function () {
            swal("获取专辑信息失败", {
                icon: "error",
            });
        }
    });
}

function deleteAlbum() {
    var ids = getSelectedRows();
    if (ids == null) {
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
                    url: "/album/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code === 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.msg, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

/**
 * 随机封面功能
 */
$('#randomCoverImage').click(function () {
    var rand = parseInt(Math.random() * 40 + 1);
    $("#albumCover").attr("src", '/admin/dist/img/rand/' + rand + ".jpg");
    
});


function reset() {
    $("#albumName").val('');
    $("#albumIcon option:first").prop("selected", 'selected');
}

// 确保新增按钮的点击事件绑定到 albumAdd 函数
$(document).on('click', '#addButton', function () {
    albumAdd();
});