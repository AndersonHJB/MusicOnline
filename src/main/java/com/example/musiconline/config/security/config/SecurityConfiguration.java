package com.example.musiconline.config.security.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.musiconline.config.satoken.utils.LoginHelper;
import com.example.musiconline.config.security.config.properties.SecurityProperties;
import com.example.musiconline.config.security.handler.AllUrlHandler;
import com.example.musiconline.constant.HttpStatus;
import com.example.musiconline.exception.SseException;
import com.example.musiconline.utils.ServletUtils;
import com.example.musiconline.utils.SpringUtils;
import com.example.musiconline.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限安全配置
 *
 * @author ll
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    AllUrlHandler allUrlHandler = SpringUtils.getBean(AllUrlHandler.class);
                    // 登录验证 -- 排除多个路径
                    SaRouter
                            // 获取所有的
                            .match(allUrlHandler.getUrls())
                            // 对未排除的路径进行检查
                            .check(() -> {
                                HttpServletRequest request = ServletUtils.getRequest();
                                // 检查是否登录 是否有token
                                try {
                                    StpUtil.checkLogin();
                                } catch (NotLoginException e) {
                                    if (request.getRequestURI().contains("sse")) {
                                        throw new SseException(e.getMessage(), e.getCode());
                                    } else {
                                        throw e;
                                    }
                                }


                            });
                })).addPathPatterns("/**")
                // 排除不需要拦截的路径
                .excludePathPatterns(securityProperties.getExcludes());
    }

    /**
     * 校验是否从网关转发
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
            .addInclude("/**")
            .addExclude("/actuator/**")
            .setAuth(obj -> {
                if (SaManager.getConfig().getCheckSameToken()) {
                    SaSameUtil.checkCurrentRequestToken();
                }
            })
            .setError(e -> SaResult.error("认证失败，无法访问系统资源").setCode(HttpStatus.UNAUTHORIZED));
    }

}
