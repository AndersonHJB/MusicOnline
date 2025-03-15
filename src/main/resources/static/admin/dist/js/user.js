$(function () {

    // 初始化 jqGrid
    $("#jqGrid").jqGrid({
        url: '/user/list',
        mtype: "POST",
        datatype: "json",
        colModel: [
            {label: 'id', name: 'userId', index: 'userId', width: 50, key: true, hidden: true},
            {label: '用户名', name: 'userName', index: 'userName', width: 100},
            {label: '邮箱', name: 'userEmail', index: 'userEmail', width: 120},
            {label: '手机号', name: 'userPhone', index: 'userPhone', width: 120},
            {label: '角色', name: 'userRole', index: 'userRole', width: 80, formatter: function(cellvalue, options, rowObject) {
                    return cellvalue === 1 ? '管理员' : '普通用户';
                }},
            {label: '状态', name: 'userStatus', index: 'userStatus', width: 80, formatter: function(cellvalue, options, rowObject) {
                    return cellvalue === 1 ? '活跃' : '禁用';
                }},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 100}
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

    // 绑定blur事件
    $("#userName").blur(function () {
        validateField(this, validUserName, "请输入符合规范的用户名！");
    });
    $("#userEmail").blur(function () {
        validateField(this, isEmail, "请输入符合规范的邮箱！");
    });
    $("#userPhone").blur(function () {
        validateField(this, isPhone, "请输入符合规范的手机号！");
    });
    $("#userPassword").blur(function () {
        validateField(this, validPassword, "请输入符合规范的密码！最少6位，最多20位字母或数字、特殊字符的组合");
    });
    $("#confirmPassword").blur(function () {
        validateField(this, function (val) {
            return val === $("#userPassword").val();
        }, "两次输入的密码不一致！");
    });

    // 绑定focus事件
    $("#userName, #userEmail, #userPhone, #userPassword, #confirmPassword").focus(function () {
        clearErrorMsg(this);
    });
});

/**
 * jqGrid重新加载
 */
function reloadUserGrid() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page,
        ajaxGridOptions: {
            contentType: "application/json"
        },
        serializeGridData: function (postData) {
            return JSON.stringify(postData);
        }
    }).trigger("reloadGrid");
}

function userAdd() {
    resetUserForm();
    $('#currentPasswordGroup').hide(); // 隐藏当前密码输入框
    $('#userPasswordGroup').hide(); // 隐藏新密码输入框
    $('#confirmPasswordGroup').hide(); // 隐藏确认密码输入框
    $('.modal-title').html('用户添加');
    $('#userModal').modal('show');
}

function userEdit() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    resetUserForm();
    // 隐藏当前密码、新密码和确认密码输入框
    $('#currentPasswordGroup').hide();
    $('#userPasswordGroup').hide();
    $('#confirmPasswordGroup').hide();
    //请求数据
    $.ajax({
        type: "POST", // 使用 POST 方法
        url: "/user/info/" + id,
        success: function (r) {
            if (r.code == 200 && r.data != null) {
                //填充数据至modal
                $("#userName").val(r.data.userName);
                $("#userEmail").val(r.data.userEmail);
                $("#userPhone").val(r.data.userPhone);
                //根据原userRole值设置select选择器为选中状态
                $("#userRole").val(Number(r.data.userRole)); // 确保 userRole 是数字
                //根据原userStatus值设置select选择器为选中状态
                $("#userStatus").val(Number(r.data.userStatus)); // 确保 userStatus 是数字
            }
        }
    });
    $('.modal-title').html('用户修改');
    $('#userModal').modal('show');
    $("#userId").val(Number(id)); // 确保 userId 是数字
}

function resetPassword() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要重置密码吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/users/resetPassword/" + id,
                success: function (r) {
                    if (r.code == 200) {
                        swal("密码重置成功", {
                            icon: "success",
                        });
                    } else {
                        swal(r.msg, {
                            icon: "error",
                        });
                    }
                }
                , error: function () {
                    swal("操作失败", {
                        icon: "error",
                    });
                }
            });
        }
    });
}

//绑定modal上的保存按钮
$('#saveUserButton').click(function () {
    var userId = $("#userId").val();
    var userName = $("#userName").val();
    var userEmail = $("#userEmail").val();
    var userPhone = $("#userPhone").val();
    var userRole = $("#userRole").val();
    var userStatus = $("#userStatus").val();

    if (!validUserName(userName)) {
        showErrorMsg("请输入符合规范的用户名！");
        return;
    }
    if (!isEmail(userEmail)) {
        showErrorMsg("请输入符合规范的邮箱！");
        return;
    }
    if (!isPhone(userPhone)) {
        showErrorMsg("请输入符合规范的手机号！");
        return;
    }

    var params = {
        userId: Number(userId), // 确保 userId 是数字
        userName: userName,
        userEmail: userEmail,
        userPhone: userPhone,
        userRole: Number(userRole), // 确保 userRole 是数字
        userStatus: Number(userStatus) // 确保 userStatus 是数字
    };

    var url = '/admin/users/save';
    if (userId != null && userId > 0) {
        url = '/user/update';
    }
    $.ajax({
        type: 'POST', //方法类型
        url: url,
        data: JSON.stringify(params),
        success: function (result) {
            if (result.code == 200) {
                $('#userModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reloadUserGrid();
            } else {
                $('#userModal').modal('hide');
                swal("保存失败", {
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
});

function deleteUser() {
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
                url: "/admin/users/delete",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.code == 200) {
                        swal("删除成功", {
                            icon: "success",
                        });
                        $("#userGrid").trigger("reloadGrid");
                    } else {
                        swal(r.msg, {
                            icon: "error",
                        });
                    }
                }
            });
        }
    });
}

function resetUserForm() {
    $("#userName").val('');
    $("#userEmail").val('');
    $("#userPhone").val('');
    $("#currentPassword").val('');
    $("#userPassword").val('');
    $("#confirmPassword").val('');
    $("#userId").val(0);
    $('#edit-error-msg').css("display", "none");
    $("#userRole option:first").prop("selected", 'selected');
    $("#userStatus option:first").prop("selected", 'selected');
    // 隐藏当前密码、新密码和确认密码输入框
    $('#currentPasswordGroup').hide();
    $('#userPasswordGroup').hide();
    $('#confirmPasswordGroup').hide();
}

function searchUsers() {
    var userName = $("#searchUserName").val();
    var userEmail = $("#searchUserEmail").val();
    var userRole = $("#searchUserRole").val();
    var userStatus = $("#searchUserStatus").val();

    var postData = {
        userName: userName,
        userEmail: userEmail,
        userRole: userRole,
        userStatus: userStatus
    };

    $("#jqGrid").jqGrid('setGridParam', {
        postData: postData,
        ajaxGridOptions: {
            contentType: "application/json"
        },
        serializeGridData: function (postData) {
            console.log("Serialized postData in searchUsers:", postData); // 添加日志
            return JSON.stringify(postData);
        }
    }).trigger("reloadGrid");
}

/**
 * 显示错误消息并设置定时器使其在几秒钟后消失
 * @param msg
 */
function showErrorMsg(msg) {
    $('#edit-error-msg').css("display", "block");
    $('#edit-error-msg').html(msg);
    setTimeout(function () {
        $('#edit-error-msg').css("display", "none");
    }, 3000); // 3秒后消失
}

/**
 * 验证字段并显示错误消息
 * @param element
 * @param validator
 * @param errorMsg
 */
function validateField(element, validator, errorMsg) {
    var value = $(element).val();
    if (!validator(value)) {
        showErrorMsg(errorMsg);
        $(element).addClass("is-invalid");
    } else {
        $(element).removeClass("is-invalid");
    }
}

/**
 * 清除错误消息
 * @param element
 */
function clearErrorMsg(element) {
    $('#edit-error-msg').css("display", "none");
    $(element).removeClass("is-invalid");
}

/**
 * 重置查询表单
 */
function resetSearchForm() {
    $('#searchUserName').val('');
    $('#searchUserEmail').val('');
    $('#searchUserRole').val('');
    $('#searchUserStatus').val('');
    searchUsers();
}

$(document).ready(function () {
    // 清除用户名输入框
    $('#clearSearchUserName').click(function () {
        $('#searchUserName').val('');
    });

    // 清除邮箱输入框
    $('#clearSearchUserEmail').click(function () {
        $('#searchUserEmail').val('');
    });

    // 重置查询表单
    $('#resetSearchForm').click(function () {
        $('#searchUserName').val('');
        $('#searchUserEmail').val('');
        $('#searchUserRole').val('');
        $('#searchUserStatus').val('');
        searchUsers();
    });
});
