package com.mystarter.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色菜单关联实体
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenuEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long roleId;
    private Long menuId;
}
