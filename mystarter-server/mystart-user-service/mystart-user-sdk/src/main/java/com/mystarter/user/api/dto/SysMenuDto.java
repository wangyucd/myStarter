package com.mystarter.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树 DTO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class SysMenuDto implements Serializable {

    private Long id;
    private Long parentId;
    private String menuName;
    private Integer menuType;
    private String path;
    private String component;
    private String permission;
    private String icon;
    private Integer sortOrder;
    private Integer visible;
    private Integer status;

    /**
     * 子菜单
     */
    private List<SysMenuDto> children;
}
