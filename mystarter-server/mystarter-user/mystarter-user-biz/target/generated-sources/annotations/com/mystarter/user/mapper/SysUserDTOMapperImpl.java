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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-18T21:28:06+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
public class SysUserDTOMapperImpl implements SysUserDTOMapper {

    @Override
    public SysUserEntity toEntity(SysUserSaveVo vo) {
        if ( vo == null ) {
            return null;
        }

        SysUserEntity sysUserEntity = new SysUserEntity();

        sysUserEntity.setId( vo.getId() );
        sysUserEntity.setUsername( vo.getUsername() );
        sysUserEntity.setPassword( vo.getPassword() );
        sysUserEntity.setNickname( vo.getNickname() );
        sysUserEntity.setEmail( vo.getEmail() );
        sysUserEntity.setPhone( vo.getPhone() );
        sysUserEntity.setAvatar( vo.getAvatar() );
        sysUserEntity.setStatus( vo.getStatus() );

        return sysUserEntity;
    }

    @Override
    public SysUserDto toDto(SysUserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SysUserDto sysUserDto = new SysUserDto();

        sysUserDto.setId( entity.getId() );
        sysUserDto.setUsername( entity.getUsername() );
        sysUserDto.setNickname( entity.getNickname() );
        sysUserDto.setEmail( entity.getEmail() );
        sysUserDto.setPhone( entity.getPhone() );
        sysUserDto.setAvatar( entity.getAvatar() );
        sysUserDto.setStatus( entity.getStatus() );
        sysUserDto.setCreateTime( entity.getCreateTime() );

        return sysUserDto;
    }

    @Override
    public List<SysUserDto> toDtos(List<SysUserEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SysUserDto> list = new ArrayList<SysUserDto>( entities.size() );
        for ( SysUserEntity sysUserEntity : entities ) {
            list.add( toDto( sysUserEntity ) );
        }

        return list;
    }

    @Override
    public SysRoleEntity toRoleEntity(SysRoleSaveVo vo) {
        if ( vo == null ) {
            return null;
        }

        SysRoleEntity sysRoleEntity = new SysRoleEntity();

        sysRoleEntity.setId( vo.getId() );
        sysRoleEntity.setRoleName( vo.getRoleName() );
        sysRoleEntity.setRoleCode( vo.getRoleCode() );
        sysRoleEntity.setDescription( vo.getDescription() );
        sysRoleEntity.setStatus( vo.getStatus() );
        sysRoleEntity.setSortOrder( vo.getSortOrder() );

        return sysRoleEntity;
    }

    @Override
    public SysRoleDto toRoleDto(SysRoleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SysRoleDto sysRoleDto = new SysRoleDto();

        sysRoleDto.setId( entity.getId() );
        sysRoleDto.setRoleName( entity.getRoleName() );
        sysRoleDto.setRoleCode( entity.getRoleCode() );
        sysRoleDto.setDescription( entity.getDescription() );
        sysRoleDto.setStatus( entity.getStatus() );
        sysRoleDto.setSortOrder( entity.getSortOrder() );
        sysRoleDto.setCreateTime( entity.getCreateTime() );

        return sysRoleDto;
    }

    @Override
    public List<SysRoleDto> toRoleDtos(List<SysRoleEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SysRoleDto> list = new ArrayList<SysRoleDto>( entities.size() );
        for ( SysRoleEntity sysRoleEntity : entities ) {
            list.add( toRoleDto( sysRoleEntity ) );
        }

        return list;
    }

    @Override
    public SysMenuEntity toMenuEntity(SysMenuSaveVo vo) {
        if ( vo == null ) {
            return null;
        }

        SysMenuEntity sysMenuEntity = new SysMenuEntity();

        sysMenuEntity.setId( vo.getId() );
        sysMenuEntity.setParentId( vo.getParentId() );
        sysMenuEntity.setMenuName( vo.getMenuName() );
        sysMenuEntity.setMenuType( vo.getMenuType() );
        sysMenuEntity.setPath( vo.getPath() );
        sysMenuEntity.setComponent( vo.getComponent() );
        sysMenuEntity.setPermission( vo.getPermission() );
        sysMenuEntity.setIcon( vo.getIcon() );
        sysMenuEntity.setSortOrder( vo.getSortOrder() );
        sysMenuEntity.setVisible( vo.getVisible() );
        sysMenuEntity.setStatus( vo.getStatus() );

        return sysMenuEntity;
    }

    @Override
    public SysMenuDto toMenuDto(SysMenuEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SysMenuDto sysMenuDto = new SysMenuDto();

        sysMenuDto.setId( entity.getId() );
        sysMenuDto.setParentId( entity.getParentId() );
        sysMenuDto.setMenuName( entity.getMenuName() );
        sysMenuDto.setMenuType( entity.getMenuType() );
        sysMenuDto.setPath( entity.getPath() );
        sysMenuDto.setComponent( entity.getComponent() );
        sysMenuDto.setPermission( entity.getPermission() );
        sysMenuDto.setIcon( entity.getIcon() );
        sysMenuDto.setSortOrder( entity.getSortOrder() );
        sysMenuDto.setVisible( entity.getVisible() );
        sysMenuDto.setStatus( entity.getStatus() );

        return sysMenuDto;
    }

    @Override
    public List<SysMenuDto> toMenuDtos(List<SysMenuEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SysMenuDto> list = new ArrayList<SysMenuDto>( entities.size() );
        for ( SysMenuEntity sysMenuEntity : entities ) {
            list.add( toMenuDto( sysMenuEntity ) );
        }

        return list;
    }
}
