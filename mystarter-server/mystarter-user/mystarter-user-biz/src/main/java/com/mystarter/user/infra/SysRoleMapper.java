package com.mystarter.user.infra;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystarter.user.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色 Mapper
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
}
