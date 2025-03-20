
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
        // 使用 MD5 加密密码
        var encryptedPassword = CryptoJS.MD5(userPassword).toString();
        
        $.ajax({
            type: 'POST', // 方法类型
            url: '/login',
            data: JSON.stringify({ userName: userName, userPassword: encryptedPassword }), // 将数据转换为 JSON 格式
            contentType: 'application/json', // 设置请求头
            success: function (result) {
                console.log(result);
                if (result.code === 200) {
                    // 保存token到localStorage，5 分钟
                    var token = 'Bearer ' + result.data.access_token;
                    var expirationTime = new Date().getTime() + 23 * 60 * 60 * 1000; // 23 小时后的时间戳
                    localStorage.setItem('token', token);
                    localStorage.setItem('tokenExpiration', expirationTime);
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

function getTokenFromSession() {
    // 这里假设 token 存储在 localStorage 中
    return localStorage.getItem('token');
}


// 检查token是否过期
function isTokenExpired() {
    var tokenExpiration = localStorage.getItem('tokenExpiration');
    if (tokenExpiration) {
        var currentTime = new Date().getTime();
        if (currentTime > tokenExpiration) {
            localStorage.removeItem('token');
            localStorage.removeItem('tokenExpiration');
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
    }else if(getTokenFromSession()){
        window.location.href = '/page/index';
    }
});
