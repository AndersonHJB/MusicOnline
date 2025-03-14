package com.example.musiconline.config.mybatis;

import cn.hutool.core.net.NetUtil;
import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.ddl.IDdl;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * MybatisPlus配置
 */
@Configuration
public class MybatisPlusConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        return interceptor;
    }

    /**
     * 分页插件，自动识别数据库类型
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        // 分页合理化
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    @Bean
    public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List<IDdl> ddlList) {
        return new DdlApplicationRunner(ddlList);
    }

    /**
     * 使用网卡信息绑定雪花生成器
     * 防止集群雪花ID重复
     */
    @Primary
    @Bean
    public IdentifierGenerator idGenerator() {
        return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
    }
}
