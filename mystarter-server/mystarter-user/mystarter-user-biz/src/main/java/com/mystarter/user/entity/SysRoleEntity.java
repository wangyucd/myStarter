package com.mystarter.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mystarter.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {

    private String roleName;
    private String roleCode;
    private String description;
    private Integer status;
    private Integer sortOrder;
}
