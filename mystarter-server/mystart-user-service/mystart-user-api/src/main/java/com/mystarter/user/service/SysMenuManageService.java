package com.mystarter.user.service;

import com.mystarter.user.api.dto.SysMenuDto;
import com.mystarter.user.api.vo.SysMenuSaveVo;

import java.util.List;

/**
 * 菜单管理 Service
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
public interface SysMenuManageService {

    /**
     * 查询菜单树
     */
    List<SysMenuDto> tree();

    /**
     * 保存菜单
     */
    void save(SysMenuSaveVo saveVo);

    /**
     * 删除菜单
     */
    void delete(Long id);

    /**
     * 根据用户ID查询菜单树（用于前端动态路由）
     */
    List<SysMenuDto> getUserMenuTree(Long userId);
}
