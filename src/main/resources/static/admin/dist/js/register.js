// 表单注册函数
$('#registerButton').click(function () {
    var userName = $('#userName').val();
    var userEmail = $('#userEmail').val();
    var userPhone = $('#userPhone').val();
    var userPassword = $('#userPassword').val();
    var confirmPassword = $('#confirmPassword').val();


    if (!validUserName(userName)) {
        swal("请输入有效用户名，3到16位（字母，数字，下划线，减号）", {
            icon: "error",
        });
        return false;
    } else if (!isEmail(userEmail)) {
        swal("请输入有效的邮箱地址", {
            icon: "error",
        });
        return false;
    } else if (!isPhone(userPhone)) {
        swal("请输入有效的手机号", {
            icon: "error",
        });
        return false;
    } else if (!validPassword(userPassword)) {
        swal("密码必须最少6位，最多20位字母或数字、特殊字符的组合", {
            icon: "error",
        });
        return false;
    } else if (confirmPassword == '') {
        swal("请确认密码", {
            icon: "error",
        });
        return false;
    } else if (userPassword !== confirmPassword) {
        swal("两次输入的密码不一致", {
            icon: "error",
        });
        return false;
    } else {
        $.ajax({
            type: 'POST', // 方法类型
            url: '/register',
            data: JSON.stringify({ userName: userName, userEmail: userEmail, userPhone: userPhone, userPassword: userPassword }), // 将数据转换为 JSON 格式
            contentType: 'application/json', // 设置请求头
            success: function (result) {
                console.log(result);
                if (result.code === 200) {
                    swal("注册成功", {
                        icon: "success",
                    }).then(() => {
                        window.location.href = '/page/login';
                    });
                } else {
                    swal(result.msg, {
                        icon: "error",
                    });
                }
            },
            error: function (xhr, status, error) {
                swal("注册失败，请稍后再试", {
                    icon: "error",
                });
            }
        });
    }
});

// 返回登录页面按钮点击事件
$('#backToLoginButton').click(function () {
    window.location.href = '/page/login';
});
