// 获取 token 的方法
function getTokenFromSession() {
    // 这里假设 token 存储在 sessionStorage 中
    return sessionStorage.getItem('token');
}

// 设置全局 ajax 请求头
$.ajaxSetup({
    headers: {
        'Authorization': getTokenFromSession()
    },
    contentType: "application/json",
    dataType: "json"
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

// 打印token
const token = getTokenFromSession();
console.log(token);

// 如果token为空，则跳转到登录页面
function checkToken() {
    if (!token) {
        swal("请登录后访问", {
            icon: "error",
        }).then(() => {
            // 用户关闭弹窗后执行跳转
            window.location.href = '/page/login';
        });
    }
}

// 登出功能
function logoutWithToken() {
    var token = getTokenFromSession();

    if (token) {
        $.ajax({
            type: "POST",
            url: '/logout',
            success: function () {
                // 退出成功，可以重定向到登录页面或其他页面
                // 清除sessionStorage中的token
                sessionStorage.removeItem('token');
                window.location.href = '/page/login';
            },
            error: function () {
                // 退出失败，可以显示错误信息
                alert('退出失败，请重试。');
            }
        });
    } else {
        // 如果没有 token，直接重定向到登录页面
        window.location.href = '/page/login';
    }
}

// 调用checkToken函数进行token验证
checkToken();