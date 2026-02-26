package com.mystarter.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mystarter.common.core.result.R;
import com.mystarter.user.api.dto.SysMenuDto;
import com.mystarter.user.api.vo.SysMenuSaveVo;
import com.mystarter.user.service.SysMenuManageService;
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
 * 菜单管理 Controller
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@RestController
@RequestMapping("/user/sysMenu")
@AllArgsConstructor
public class SysMenuController {

    private final SysMenuManageService sysMenuManageService;

    /**
     * 查询菜单树
     *
     * @return 菜单树列表
     */
    @GetMapping("/tree")
    @SaCheckPermission("system:menu:list")
    public R<List<SysMenuDto>> tree() {
        return R.ok(sysMenuManageService.tree());
    }

    /**
     * 保存菜单
     *
     * @param saveVo 菜单保存参数
     * @return 操作结果
     */
    @PostMapping("/save")
    @SaCheckPermission("system:menu:add")
    public R<Void> save(@Valid @RequestBody SysMenuSaveVo saveVo) {
        sysMenuManageService.save(saveVo);
        return R.ok();
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("system:menu:delete")
    public R<Void> delete(@PathVariable Long id) {
        sysMenuManageService.delete(id);
        return R.ok();
    }
}
