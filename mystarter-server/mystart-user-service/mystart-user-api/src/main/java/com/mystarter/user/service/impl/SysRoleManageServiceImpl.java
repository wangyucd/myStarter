package com.mystarter.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mystarter.user.api.dto.SysRoleDto;
import com.mystarter.user.api.vo.SysRoleSaveVo;
import com.mystarter.user.entity.SysRoleEntity;
import com.mystarter.user.entity.SysRoleMenuEntity;
import com.mystarter.user.infra.SysRoleMapper;
import com.mystarter.user.infra.SysRoleMenuMapper;
import com.mystarter.user.mapper.SysUserDTOMapper;
import com.mystarter.user.service.SysRoleManageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理 Service 实现
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysRoleManageServiceImpl implements SysRoleManageService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysRoleDto> list() {
        List<SysRoleEntity> entities = sysRoleMapper.selectList(
                new LambdaQueryWrapper<SysRoleEntity>()
                        .orderByAsc(SysRoleEntity::getSortOrder)
        );
        return SysUserDTOMapper.INSTANCE.toRoleDtos(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleSaveVo saveVo) {
        SysRoleEntity entity = SysUserDTOMapper.INSTANCE.toRoleEntity(saveVo);

        if (saveVo.getId() == null) {
            sysRoleMapper.insert(entity);
        } else {
            sysRoleMapper.updateById(entity);
        }

        // 更新角色菜单关联
        if (CollUtil.isNotEmpty(saveVo.getMenuIds())) {
            // 先删除旧的关联
            sysRoleMenuMapper.delete(
                    new LambdaQueryWrapper<SysRoleMenuEntity>()
                            .eq(SysRoleMenuEntity::getRoleId, entity.getId())
            );
            // 再插入新的关联
            for (Long menuId : saveVo.getMenuIds()) {
                SysRoleMenuEntity roleMenu = new SysRoleMenuEntity();
                roleMenu.setRoleId(entity.getId());
                roleMenu.setMenuId(menuId);
                sysRoleMenuMapper.insert(roleMenu);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        sysRoleMapper.deleteById(id);
        // 删除角色菜单关联
        sysRoleMenuMapper.delete(
                new LambdaQueryWrapper<SysRoleMenuEntity>()
                        .eq(SysRoleMenuEntity::getRoleId, id)
        );
    }
}
