$(function () {
    // 页面加载时获取用户信息
    $.ajax({
        type: "GET",
        url: "/user/info", // 假设这是获取用户信息的接口
        success: function (data) {
            $('#userName').val(data.data.userName);
            $('#userEmail').val(data.data.userEmail);
            $('#userPhone').val(data.data.userPhone);
            $('#userId').val(data.data.userId); // 设置用户ID
        },
        error: function () {
            swal("获取用户信息失败", {
                icon: "error",
            });
        }
    });

    //修改个人信息
    $('#updateUserNameButton').click(function () {
        $("#updateUserNameButton").attr("disabled",true);
        var userName = $('#userName').val();
        var userEmail = $('#userEmail').val();
        var userPhone = $('#userPhone').val();
        var userId = $('#userId').val(); // 获取用户ID
        if (validUserNameForUpdate(userName, userEmail, userPhone)) {
            //ajax提交数据
            var params = {
                userName: userName,
                userEmail: userEmail,
                userPhone: userPhone,
                userId: userId
            };
            $.ajax({
                type: "POST",
                url: "/user/update",
                contentType: "application/json", // 设置请求头
                data: JSON.stringify(params), // 将数据转换为JSON字符串
                success: function (r) {
                    if (r.code === 200) {
                        swal("修改成功", {
                            icon: "success",
                        }).then(
                            function () {
                                window.location.reload();
                            }
                        );
                    } else {
                        swal(r.msg, {
                            icon: "error",
                        });
                    }
                }
            });
        } else {
            $("#updateUserNameButton").prop("disabled",false);
        }
    });
    //修改密码
    $('#updatePasswordButton').click(function () {
        $("#updatePasswordButton").attr("disabled",true);
        var originalPassword = $('#originalPassword').val();
        var newPassword = $('#newPassword').val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            var params = {
                originalPassword: originalPassword,
                newPassword: newPassword
            };
            $.ajax({
                type: "POST",
                url: "/user/updatePassword",
                contentType: "application/json", // 设置请求头
                data: JSON.stringify(params), // 将数据转换为JSON字符串
                success: function (r) {
                    if (r.code === 200) {
                        swal("修改成功", {
                            icon: "success",
                        }).then(
                            function () {
                                logoutWithToken();
                                window.location.href = '/page/login';
                            }
                        );
                    } else {
                        swal(r.msg, {
                            icon: "error",
                        });
                    }
                }
            });
        } else {
            $("#updatePasswordButton").attr("disabled",false);
        }
    });
})

/**
 * 名称验证
 */
function validUserNameForUpdate(userName, userEmail, userPhone) {
    if (isNull(userName) || userName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入用户账号！");
        return false;
    }
    if (isNull(userEmail) || userEmail.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入用户邮箱！");
        return false;
    }
    if (isNull(userPhone) || userPhone.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入手机号码！");
        return false;
    }
    if (!validUserName(userName)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的用户账号！");
        return false;
    }
    if (!validEmail(userEmail)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的用户邮箱！");
        return false;
    }
    if (!validPhone(userPhone)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的手机号码！");
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("新密码不能为空！");
        return false;
    }
    if (!validPassword(newPassword)) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入符合规范的密码！最少6位，最多20位字母或数字、特殊字符的组合");
        return false;
    }
    return true;
}

// 邮箱验证
function validEmail(email) {
    var re = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return re.test(email);
}

// 手机号验证
function validPhone(phone) {
    var re = /^1[3-9]\d{9}$/;
    return re.test(phone);
}