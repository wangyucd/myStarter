package com.mystarter.user.api.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色保存 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class SysRoleSaveVo implements Serializable {

    private Long id;

    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    private String description;

    private Integer status;

    private Integer sortOrder;

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;
}
