package com.mystarter.user.service;

import com.mystarter.common.core.page.PageResult;
import com.mystarter.user.api.dto.SysRoleDto;
import com.mystarter.user.api.vo.SysRoleSaveVo;

import java.util.List;

/**
 * 角色管理 Service
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
public interface SysRoleManageService {

    /**
     * 查询所有角色
     */
    List<SysRoleDto> list();

    /**
     * 保存角色
     */
    void save(SysRoleSaveVo saveVo);

    /**
     * 删除角色
     */
    void delete(Long id);
}
