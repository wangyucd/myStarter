package com.mystarter.common.core.page;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基类
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class PageQuery implements Serializable {

    /**
     * 页码（默认第1页）
     */
    private Integer pageNum = 1;

    /**
     * 每页大小（默认10条）
     */
    private Integer pageSize = 10;
}
