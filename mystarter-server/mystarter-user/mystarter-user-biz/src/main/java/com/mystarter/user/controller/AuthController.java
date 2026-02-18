package com.mystarter.user.controller;

import com.mystarter.common.core.result.R;
import com.mystarter.user.api.dto.LoginDto;
import com.mystarter.user.api.dto.SysMenuDto;
import com.mystarter.user.api.vo.LoginVo;
import com.mystarter.user.service.SysMenuManageService;
import com.mystarter.user.service.SysUserManageService;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 认证 Controller
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@RestController
@RequestMapping("/user/auth")
@AllArgsConstructor
public class AuthController {

    private final SysUserManageService sysUserManageService;
    private final SysMenuManageService sysMenuManageService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public R<LoginDto> login(@Valid @RequestBody LoginVo loginVo) {
        return R.ok(sysUserManageService.login(loginVo));
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public R<Void> logout() {
        sysUserManageService.logout();
        return R.ok();
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/userInfo")
    public R<LoginDto> getUserInfo() {
        return R.ok(sysUserManageService.getUserInfo());
    }

    /**
     * 获取当前用户菜单树（用于前端动态路由）
     */
    @GetMapping("/menus")
    public R<List<SysMenuDto>> getUserMenus() {
        long userId = StpUtil.getLoginIdAsLong();
        return R.ok(sysMenuManageService.getUserMenuTree(userId));
    }
}
