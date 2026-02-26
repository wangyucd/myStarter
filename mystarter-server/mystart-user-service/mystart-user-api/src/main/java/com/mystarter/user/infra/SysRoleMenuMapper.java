package com.mystarter.user.infra;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystarter.user.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关联 Mapper
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {
}
