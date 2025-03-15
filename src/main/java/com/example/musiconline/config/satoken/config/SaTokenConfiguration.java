package com.example.musiconline.config.satoken.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import com.example.musiconline.config.factory.YmlPropertySourceFactory;
import com.example.musiconline.config.satoken.core.dao.PlusSaTokenDao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Sa-Token 配置
 *
 * @author ll
 */
@Configuration
@PropertySource(value = "classpath:common-satoken.yml", factory = YmlPropertySourceFactory.class)
public class SaTokenConfiguration {

    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }


//    /**
//     * 自定义dao层存储
//     */
//    @Bean
//    public SaTokenDao saTokenDao() {
//        return new PlusSaTokenDao();
//    }

}
