$(function () {
    $("#jqGrid").jqGrid({
        url: '/user/list',
        method: "POST",
        datatype: "json",
        colModel: [
            {label: 'id', name: 'userId', index: 'userId', width: 50, key: true, hidden: true},
            {label: '用户名', name: 'userName', index: 'userName', width: 100},
            {label: '邮箱', name: 'userEmail', index: 'userEmail', width: 120},
            {label: '手机号', name: 'userPhone', index: 'userPhone', width: 120},
            {label: '角色', name: 'userRole', index: 'userRole', width: 80},
            {label: '状态', name: 'userStatus', index: 'userStatus', width: 80},
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
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
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
        page: page
    }).trigger("reloadGrid");
}

function userAdd() {
    resetUserForm();
    $('#currentPasswordGroup').hide(); // 隐藏当前密码输入框
    $('.modal-title').html('用户添加');
    $('#userModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveUserButton').click(function () {
    var userId = $("#userId").val();
    var userName = $("#userName").val();
    var userEmail = $("#userEmail").val();
    var userPhone = $("#userPhone").val();
    var currentPassword = $("#currentPassword").val();
    var userPassword = $("#userPassword").val();
    var confirmPassword = $("#confirmPassword").val();
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
    if (!validPassword(userPassword)) {
        showErrorMsg("请输入符合规范的密码！最少6位，最多20位字母或数字、特殊字符的组合");
        return;
    }
    if (userPassword !== confirmPassword) {
        showErrorMsg("两次输入的密码不一致！");
        return;
    }
    if (userId != null && userId > 0 && isNull(currentPassword)) {
        showErrorMsg("请输入当前密码！");
        return;
    }

    var params = $("#userForm").serialize();
    var url = '/admin/users/save';
    if (userId != null && userId > 0) {
        url = '/admin/users/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200 && result.data) {
                $('#userModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reloadUserGrid();
            }
            else {
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

function userEdit() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    resetUserForm();
    $('#currentPasswordGroup').show(); // 显示当前密码输入框
    //请求数据
    $.get("/admin/users/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#userName").val(r.data.userName);
            $("#userEmail").val(r.data.userEmail);
            $("#userPhone").val(r.data.userPhone);
            //根据原userRole值设置select选择器为选中状态
            if (r.data.userRole == 'admin') {
                $("#userRole option:eq(1)").prop("selected", 'selected');
            }
            //根据原userStatus值设置select选择器为选中状态
            if (r.data.userStatus == 'inactive') {
                $("#userStatus option:eq(1)").prop("selected", 'selected');
            }
        }
    });
    $('.modal-title').html('用户修改');
    $('#userModal').modal('show');
    $("#userId").val(id);
}

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
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        swal("删除成功", {
                            icon: "success",
                        });
                        $("#userGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
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
}

function searchUsers() {
    var userName = $("#searchUserName").val();
    var userEmail = $("#searchUserEmail").val();
    var userRole = $("#searchUserRole").val();
    var userStatus = $("#searchUserStatus").val();

    $("#jqGrid").jqGrid('setGridParam', {
        postData: {
            userName: userName,
            userEmail: userEmail,
            userRole: userRole,
            userStatus: userStatus
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
