package com.mystarter.product.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类树 DTO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class CategoryDto implements Serializable {

    private Long id;
    private Long parentId;
    private String categoryName;
    private String icon;
    private Integer sortOrder;
    private Integer status;
    private List<CategoryDto> children;
}
