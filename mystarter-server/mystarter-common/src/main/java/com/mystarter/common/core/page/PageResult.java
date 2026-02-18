package com.mystarter.common.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(Long total, List<T> records) {
        return new PageResult<>(total, records);
    }
}
