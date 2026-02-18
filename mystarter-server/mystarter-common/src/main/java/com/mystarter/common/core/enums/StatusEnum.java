package com.mystarter.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    /**
     * 禁用
     */
    DISABLE(0, "禁用"),

    /**
     * 启用
     */
    ENABLE(1, "启用");

    private final int code;
    private final String desc;
}
