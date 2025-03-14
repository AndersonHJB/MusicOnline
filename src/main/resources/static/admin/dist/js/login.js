// 表单登录函数
// 表单登录函数
$('#loginButton').click(function () {
    var userName = $('#userName').val();
    var password = $('#password').val();
    if (userName == '') {
        swal("请输入用户名", {
            icon: "error",
        });
        return false;
    } else if (password == '') {
        swal("请输入密码", {
            icon: "error",
        });
        return false;
    } else {
        $.ajax({
            type: 'POST', // 方法类型
            url: '/login',
            data: JSON.stringify({ userName: userName, password: password }), // 将数据转换为 JSON 格式
            contentType: 'application/json', // 设置请求头
            success: function (result) {
                console.log(result);
                if (result.code === 200) {
                    // 保存token到session，30 分钟
                    var token = 'Bearer ' + result.data.access_token;
                    var expirationTime = new Date().getTime() + 30 * 1000; // 30 分钟后的时间戳
                    sessionStorage.setItem('token', token);
                    sessionStorage.setItem('tokenExpiration', expirationTime);
                    window.location.href = '/page/index';
                } else {
                }
            },
            error: function (xhr, status, error) {
            }
        });
    }
});

// 检查token是否过期
function isTokenExpired() {
    var tokenExpiration = sessionStorage.getItem('tokenExpiration');
    if (tokenExpiration) {
        var currentTime = new Date().getTime();
        if (currentTime > tokenExpiration) {
            sessionStorage.removeItem('token');
            sessionStorage.removeItem('tokenExpiration');
            return true;
        }
    }
    return false;
}

// 在需要使用token的地方调用isTokenExpired函数进行检查
// 例如，在页面加载时检查token是否过期
$(document).ready(function () {
    if (isTokenExpired()) {
        swal("登录已过期，请重新登录", {
            icon: "warning",
        }).then(() => {
            // 用户关闭弹窗后执行跳转
            window.location.href = '/page/login';
        });
    }
});
