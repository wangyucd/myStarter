package com.mystarter.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 网关鉴权配置
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Configuration
public class SaTokenConfig {

    /**
     * 注册 Sa-Token 路由鉴权过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截所有路由
                .addInclude("/**")
                // 放行登录接口、静态资源
                .addExclude("/user/auth/login")
                .addExclude("/favicon.ico")
                .addExclude("/actuator/**")
                // 鉴权逻辑
                .setAuth(obj -> {
                    // 登录校验：所有接口需要登录
                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                })
                // 异常处理
                .setError(e -> "{\"code\":401,\"msg\":\"" + e.getMessage() + "\",\"data\":null}");
    }
}
