package com.mystarter.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色信息 DTO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class SysRoleDto implements Serializable {

    private Long id;
    private String roleName;
    private String roleCode;
    private String description;
    private Integer status;
    private Integer sortOrder;
    private LocalDateTime createTime;
}
