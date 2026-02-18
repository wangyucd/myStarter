package com.mystarter.user.api.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单保存 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class SysMenuSaveVo implements Serializable {

    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 类型：1-目录 2-菜单 3-按钮
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否可见
     */
    private Integer visible;

    /**
     * 状态
     */
    private Integer status;
}
