// 表单登录函数
// 表单登录函数
$('#loginButton').click(function () {
    var userName = $('#userName').val();
    var userPassword = $('#userPassword').val();
    if (userName == '') {
        swal("请输入用户名", {
            icon: "error",
        });
        return false;
    } else if (userPassword == '') {
        swal("请输入密码", {
            icon: "error",
        });
        return false;
    } else {
        $.ajax({
            type: 'POST', // 方法类型
            url: '/login',
            data: JSON.stringify({ userName: userName, userPassword: userPassword }), // 将数据转换为 JSON 格式
            contentType: 'application/json', // 设置请求头
            success: function (result) {
                console.log(result);
                if (result.code === 200) {
                    // 保存token到session，5 分钟
                    var token = 'Bearer ' + result.data.access_token;
                    var expirationTime = new Date().getTime() + 5 * 60 * 1000; // 5 分钟后的时间戳
                    sessionStorage.setItem('token', token);
                    sessionStorage.setItem('tokenExpiration', expirationTime);
                    window.location.href = '/page/index';
                } else {
                    swal(result.msg, {
                        icon: "error",
                    });
                }
            },
            error: function (xhr, status, error) {
            }
        });
    }
});

