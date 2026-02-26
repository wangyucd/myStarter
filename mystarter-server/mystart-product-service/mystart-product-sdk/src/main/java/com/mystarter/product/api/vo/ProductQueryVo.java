package com.mystarter.product.api.vo;

import com.mystarter.common.core.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 商品分页查询 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductQueryVo extends PageQuery implements Serializable {

    /**
     * 商品名称（模糊搜索）
     */
    private String productName;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 状态
     */
    private Integer status;
}
