package com.mystarter.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    /**
     * 目录
     */
    DIRECTORY(1, "目录"),

    /**
     * 菜单
     */
    MENU(2, "菜单"),

    /**
     * 按钮
     */
    BUTTON(3, "按钮");

    private final int code;
    private final String desc;
}
