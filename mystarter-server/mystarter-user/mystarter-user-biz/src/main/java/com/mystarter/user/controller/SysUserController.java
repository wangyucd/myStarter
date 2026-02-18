package com.mystarter.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mystarter.common.core.page.PageResult;
import com.mystarter.common.core.result.R;
import com.mystarter.user.api.dto.SysUserDto;
import com.mystarter.user.api.vo.SysUserQueryVo;
import com.mystarter.user.api.vo.SysUserSaveVo;
import com.mystarter.user.service.SysUserManageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理 Controller
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@RestController
@RequestMapping("/user/sysUser")
@AllArgsConstructor
public class SysUserController {

    private final SysUserManageService sysUserManageService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @SaCheckPermission("system:user:list")
    public R<PageResult<SysUserDto>> page(SysUserQueryVo queryVo) {
        return R.ok(sysUserManageService.page(queryVo));
    }

    /**
     * 新增或编辑用户
     */
    @PostMapping("/save")
    @SaCheckPermission("system:user:add")
    public R<Void> save(@Valid @RequestBody SysUserSaveVo saveVo) {
        sysUserManageService.save(saveVo);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:user:delete")
    public R<Void> delete(@PathVariable Long id) {
        sysUserManageService.delete(id);
        return R.ok();
    }
}
