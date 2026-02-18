package com.mystarter.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mystarter.common.core.enums.MenuTypeEnum;
import com.mystarter.user.api.dto.SysMenuDto;
import com.mystarter.user.api.vo.SysMenuSaveVo;
import com.mystarter.user.entity.SysMenuEntity;
import com.mystarter.user.entity.SysRoleMenuEntity;
import com.mystarter.user.entity.SysUserRoleEntity;
import com.mystarter.user.infra.SysMenuMapper;
import com.mystarter.user.infra.SysRoleMenuMapper;
import com.mystarter.user.infra.SysUserRoleMapper;
import com.mystarter.user.mapper.SysUserDTOMapper;
import com.mystarter.user.service.SysMenuManageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理 Service 实现
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysMenuManageServiceImpl implements SysMenuManageService {

    private final SysMenuMapper sysMenuMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenuDto> tree() {
        // 查询所有菜单
        List<SysMenuEntity> allMenus = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenuEntity>()
                        .orderByAsc(SysMenuEntity::getSortOrder)
        );
        List<SysMenuDto> dtos = SysUserDTOMapper.INSTANCE.toMenuDtos(allMenus);
        // 构建树形结构
        return buildTree(dtos, 0L);
    }

    @Override
    public void save(SysMenuSaveVo saveVo) {
        SysMenuEntity entity = SysUserDTOMapper.INSTANCE.toMenuEntity(saveVo);
        if (saveVo.getParentId() == null) {
            entity.setParentId(0L);
        }
        if (saveVo.getId() == null) {
            sysMenuMapper.insert(entity);
        } else {
            sysMenuMapper.updateById(entity);
        }
    }

    @Override
    public void delete(Long id) {
        // 删除菜单及其子菜单
        sysMenuMapper.deleteById(id);
        sysMenuMapper.delete(
                new LambdaQueryWrapper<SysMenuEntity>()
                        .eq(SysMenuEntity::getParentId, id)
        );
    }

    @Override
    public List<SysMenuDto> getUserMenuTree(Long userId) {
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

        // 查询角色菜单关联
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

        // 查询菜单（排除按钮类型，按钮不需要生成路由）
        List<SysMenuEntity> menus = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenuEntity>()
                        .in(SysMenuEntity::getId, menuIds)
                        .ne(SysMenuEntity::getMenuType, MenuTypeEnum.BUTTON.getCode())
                        .eq(SysMenuEntity::getStatus, 1)
                        .orderByAsc(SysMenuEntity::getSortOrder)
        );

        List<SysMenuDto> dtos = SysUserDTOMapper.INSTANCE.toMenuDtos(menus);
        return buildTree(dtos, 0L);
    }

    /**
     * 构建菜单树
     */
    private List<SysMenuDto> buildTree(List<SysMenuDto> allMenus, Long parentId) {
        List<SysMenuDto> tree = new ArrayList<>();
        for (SysMenuDto menu : allMenus) {
            if (parentId.equals(menu.getParentId())) {
                menu.setChildren(buildTree(allMenus, menu.getId()));
                tree.add(menu);
            }
        }
        return tree;
    }
}
