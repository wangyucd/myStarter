package com.mystarter.user.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mystarter.common.core.exception.BizException;
import com.mystarter.common.core.page.PageResult;
import com.mystarter.user.api.dto.LoginDto;
import com.mystarter.user.api.dto.SysUserDto;
import com.mystarter.user.api.vo.LoginVo;
import com.mystarter.user.api.vo.SysUserQueryVo;
import com.mystarter.user.api.vo.SysUserSaveVo;
import com.mystarter.user.entity.SysMenuEntity;
import com.mystarter.user.entity.SysRoleEntity;
import com.mystarter.user.entity.SysRoleMenuEntity;
import com.mystarter.user.entity.SysUserEntity;
import com.mystarter.user.entity.SysUserRoleEntity;
import com.mystarter.user.infra.SysMenuMapper;
import com.mystarter.user.infra.SysRoleMapper;
import com.mystarter.user.infra.SysRoleMenuMapper;
import com.mystarter.user.infra.SysUserMapper;
import com.mystarter.user.infra.SysUserRoleMapper;
import com.mystarter.user.mapper.SysUserDTOMapper;
import com.mystarter.user.service.SysUserManageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理 Service 实现
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserManageServiceImpl implements SysUserManageService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public LoginDto login(LoginVo loginVo) {
        // 根据用户名查询用户
        SysUserEntity user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUserEntity>()
                        .eq(SysUserEntity::getUsername, loginVo.getUsername())
        );
        if (user == null) {
            throw new BizException("用户名或密码错误");
        }

        // 校验密码
        if (!BCrypt.checkpw(loginVo.getPassword(), user.getPassword())) {
            throw new BizException("用户名或密码错误");
        }

        // 检查用户状态
        if (user.getStatus() != 1) {
            throw new BizException("用户已被禁用");
        }

        // Sa-Token 登录
        StpUtil.login(user.getId());

        // 查询用户权限列表
        List<String> permissions = getUserPermissions(user.getId());

        // 构建登录响应
        LoginDto loginDto = new LoginDto();
        loginDto.setToken(StpUtil.getTokenValue());
        loginDto.setUserId(user.getId());
        loginDto.setUsername(user.getUsername());
        loginDto.setNickname(user.getNickname());
        loginDto.setAvatar(user.getAvatar());
        loginDto.setPermissions(permissions);

        return loginDto;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public LoginDto getUserInfo() {
        // 获取当前登录用户ID
        long userId = StpUtil.getLoginIdAsLong();
        SysUserEntity user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        // 查询权限
        List<String> permissions = getUserPermissions(userId);

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(StpUtil.getTokenValue());
        loginDto.setUserId(user.getId());
        loginDto.setUsername(user.getUsername());
        loginDto.setNickname(user.getNickname());
        loginDto.setAvatar(user.getAvatar());
        loginDto.setPermissions(permissions);

        return loginDto;
    }

    @Override
    public PageResult<SysUserDto> page(SysUserQueryVo queryVo) {
        // 构建查询条件
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(queryVo.getUsername()), SysUserEntity::getUsername, queryVo.getUsername());
        wrapper.like(StrUtil.isNotBlank(queryVo.getNickname()), SysUserEntity::getNickname, queryVo.getNickname());
        wrapper.eq(queryVo.getStatus() != null, SysUserEntity::getStatus, queryVo.getStatus());
        wrapper.orderByDesc(SysUserEntity::getCreateTime);

        // 分页查询
        Page<SysUserEntity> page = new Page<>(queryVo.getPageNum(), queryVo.getPageSize());
        sysUserMapper.selectPage(page, wrapper);

        // 转换结果
        List<SysUserDto> dtos = SysUserDTOMapper.INSTANCE.toDtos(page.getRecords());

        return PageResult.of(page.getTotal(), dtos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysUserSaveVo saveVo) {
        SysUserEntity entity = SysUserDTOMapper.INSTANCE.toEntity(saveVo);

        if (saveVo.getId() == null) {
            // 新增：检查用户名是否已存在
            Long count = sysUserMapper.selectCount(
                    new LambdaQueryWrapper<SysUserEntity>()
                            .eq(SysUserEntity::getUsername, saveVo.getUsername())
            );
            if (count > 0) {
                throw new BizException("用户名已存在");
            }
            // 密码加密
            entity.setPassword(BCrypt.hashpw(saveVo.getPassword()));
            sysUserMapper.insert(entity);
        } else {
            // 编辑：密码不为空时才更新密码
            if (StrUtil.isNotBlank(saveVo.getPassword())) {
                entity.setPassword(BCrypt.hashpw(saveVo.getPassword()));
            } else {
                entity.setPassword(null);
            }
            sysUserMapper.updateById(entity);
        }

        // 更新用户角色关联
        if (CollUtil.isNotEmpty(saveVo.getRoleIds())) {
            // 先删除旧的关联
            sysUserRoleMapper.delete(
                    new LambdaQueryWrapper<SysUserRoleEntity>()
                            .eq(SysUserRoleEntity::getUserId, entity.getId())
            );
            // 再插入新的关联
            for (Long roleId : saveVo.getRoleIds()) {
                SysUserRoleEntity userRole = new SysUserRoleEntity();
                userRole.setUserId(entity.getId());
                userRole.setRoleId(roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 删除用户
        sysUserMapper.deleteById(id);
        // 删除用户角色关联
        sysUserRoleMapper.delete(
                new LambdaQueryWrapper<SysUserRoleEntity>()
                        .eq(SysUserRoleEntity::getUserId, id)
        );
    }

    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return 权限码列表
     */
    private List<String> getUserPermissions(Long userId) {
        // 查询用户拥有的角色ID
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

        // 查询角色拥有的菜单ID
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

        // 查询菜单的权限标识
        List<SysMenuEntity> menus = sysMenuMapper.selectBatchIds(menuIds);
        return menus.stream()
                .map(SysMenuEntity::getPermission)
                .filter(StrUtil::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
    }
}
