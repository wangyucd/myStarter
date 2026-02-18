package com.mystarter.user.mapper;

import com.mystarter.user.api.dto.SysMenuDto;
import com.mystarter.user.api.dto.SysRoleDto;
import com.mystarter.user.api.dto.SysUserDto;
import com.mystarter.user.api.vo.SysMenuSaveVo;
import com.mystarter.user.api.vo.SysRoleSaveVo;
import com.mystarter.user.api.vo.SysUserSaveVo;
import com.mystarter.user.entity.SysMenuEntity;
import com.mystarter.user.entity.SysRoleEntity;
import com.mystarter.user.entity.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户模块 MapStruct 转换器
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Mapper
public interface SysUserDTOMapper {

    SysUserDTOMapper INSTANCE = Mappers.getMapper(SysUserDTOMapper.class);

    /**
     * VO 转用户实体
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    SysUserEntity toEntity(SysUserSaveVo vo);

    /**
     * 用户实体转 DTO
     */
    @Mapping(target = "roleNames", ignore = true)
    SysUserDto toDto(SysUserEntity entity);

    /**
     * 用户实体列表转 DTO 列表
     */
    List<SysUserDto> toDtos(List<SysUserEntity> entities);

    /**
     * VO 转角色实体
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    SysRoleEntity toRoleEntity(SysRoleSaveVo vo);

    /**
     * 角色实体转 DTO
     */
    SysRoleDto toRoleDto(SysRoleEntity entity);

    /**
     * 角色实体列表转 DTO 列表
     */
    List<SysRoleDto> toRoleDtos(List<SysRoleEntity> entities);

    /**
     * VO 转菜单实体
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    SysMenuEntity toMenuEntity(SysMenuSaveVo vo);

    /**
     * 菜单实体转 DTO
     */
    @Mapping(target = "children", ignore = true)
    SysMenuDto toMenuDto(SysMenuEntity entity);

    /**
     * 菜单实体列表转 DTO 列表
     */
    List<SysMenuDto> toMenuDtos(List<SysMenuEntity> entities);
}
