package com.mystarter.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mystarter.common.core.result.R;
import com.mystarter.user.api.dto.SysRoleDto;
import com.mystarter.user.api.vo.SysRoleSaveVo;
import com.mystarter.user.service.SysRoleManageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理 Controller
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@RestController
@RequestMapping("/user/sysRole")
@AllArgsConstructor
public class SysRoleController {

    private final SysRoleManageService sysRoleManageService;

    /**
     * 查询角色列表
     */
    @GetMapping("/list")
    @SaCheckPermission("system:role:list")
    public R<List<SysRoleDto>> list() {
        return R.ok(sysRoleManageService.list());
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    @SaCheckPermission("system:role:add")
    public R<Void> save(@Valid @RequestBody SysRoleSaveVo saveVo) {
        sysRoleManageService.save(saveVo);
        return R.ok();
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:role:delete")
    public R<Void> delete(@PathVariable Long id) {
        sysRoleManageService.delete(id);
        return R.ok();
    }
}
