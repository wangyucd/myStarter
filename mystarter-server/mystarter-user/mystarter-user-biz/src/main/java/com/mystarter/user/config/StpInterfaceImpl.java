package com.mystarter.user.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mystarter.user.entity.SysMenuEntity;
import com.mystarter.user.entity.SysRoleEntity;
import com.mystarter.user.entity.SysRoleMenuEntity;
import com.mystarter.user.entity.SysUserRoleEntity;
import com.mystarter.user.infra.SysMenuMapper;
import com.mystarter.user.infra.SysRoleMapper;
import com.mystarter.user.infra.SysRoleMenuMapper;
import com.mystarter.user.infra.SysUserRoleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sa-Token 权限认证接口实现
 * <p>
 * Sa-Token 在鉴权时会调用此接口获取用户的权限和角色列表
 * </p>
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Component
@AllArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysMenuMapper sysMenuMapper;

    /**
     * 获取用户权限码列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        long userId = Long.parseLong(loginId.toString());

        // 查询用户角色
        List<SysUserRoleEntity> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRoleEntity>()
                        .eq(SysUserRoleEntity::getUserId, userId)
        );
        if (CollUtil.isEmpty(userRoles)) {
            return Collections.emptyList();
        }

        List<Long> roleIds = userRoles.stream()
                .map(SysUserRoleEntity::getRoleId)
                .collect(Collectors.toList());

        // 查询角色菜单
        List<SysRoleMenuEntity> roleMenus = sysRoleMenuMapper.selectList(
                new LambdaQueryWrapper<SysRoleMenuEntity>()
                        .in(SysRoleMenuEntity::getRoleId, roleIds)
        );
        if (CollUtil.isEmpty(roleMenus)) {
            return Collections.emptyList();
        }

        List<Long> menuIds = roleMenus.stream()
                .map(SysRoleMenuEntity::getMenuId)
                .distinct()
                .collect(Collectors.toList());

        // 查询权限标识
        List<SysMenuEntity> menus = sysMenuMapper.selectBatchIds(menuIds);
        return menus.stream()
                .map(SysMenuEntity::getPermission)
                .filter(StrUtil::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 获取用户角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        long userId = Long.parseLong(loginId.toString());

        // 查询用户角色
        List<SysUserRoleEntity> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRoleEntity>()
                        .eq(SysUserRoleEntity::getUserId, userId)
        );
        if (CollUtil.isEmpty(userRoles)) {
            return Collections.emptyList();
        }

        List<Long> roleIds = userRoles.stream()
                .map(SysUserRoleEntity::getRoleId)
                .collect(Collectors.toList());

        // 查询角色编码
        List<SysRoleEntity> roles = sysRoleMapper.selectBatchIds(roleIds);
        return roles.stream()
                .map(SysRoleEntity::getRoleCode)
                .collect(Collectors.toList());
    }
}
