package com.example.musiconline.config.satoken.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.example.musiconline.model.LoginUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

/**
 * 登录鉴权助手
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    public static final String LOGIN_USER_KEY = "loginUser";
    public static final String USER_KEY = "userId";
    public static final String CLIENT_KEY = "clientid";

    /**
     * 登录系统
     * @param loginUser 登录用户信息
     * @param model     配置参数
     */
    public static void login(LoginUser loginUser, SaLoginModel model) {
        SaStorage storage = SaHolder.getStorage();
        storage.set(LOGIN_USER_KEY, loginUser);
        storage.set(USER_KEY, loginUser.getUserId());
        model = ObjectUtil.defaultIfNull(model, new SaLoginModel());
        StpUtil.login(loginUser.getLoginId(),
            model.setExtra(USER_KEY, loginUser.getUserId()));
        StpUtil.getTokenSession().set(LOGIN_USER_KEY, loginUser);
    }

    /**
     * 获取用户(多级缓存)
     */
    public static LoginUser getLoginUser() {
        return (LoginUser) getStorageIfAbsentSet(LOGIN_USER_KEY, () -> {
            SaSession session = StpUtil.getTokenSession();
            if (ObjectUtil.isNull(session)) {
                return null;
            }
            return session.get(LOGIN_USER_KEY);
        });
    }

    /**
     * 获取用户基于token
     */
    public static LoginUser getLoginUser(String token) {
        SaSession session = StpUtil.getTokenSessionByToken(token);
        if (ObjectUtil.isNull(session)) {
            return null;
        }
        return (LoginUser) session.get(LOGIN_USER_KEY);
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return  Convert.toLong(getExtra(USER_KEY));
    }



    private static Object getExtra(String key) {
        return getStorageIfAbsentSet(key, () -> StpUtil.getExtra(key));
    }

    /**
     * 获取用户账户
     */
    public static String getUsername() {
        return getLoginUser().getUserName();
    }






    public static boolean isLogin() {
        return getLoginUser() != null;
    }

    public static Object getStorageIfAbsentSet(String key, Supplier<Object> handle) {
        try {
            Object obj = SaHolder.getStorage().get(key);
            if (ObjectUtil.isNull(obj)) {
                obj = handle.get();
                SaHolder.getStorage().set(key, obj);
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }
}
