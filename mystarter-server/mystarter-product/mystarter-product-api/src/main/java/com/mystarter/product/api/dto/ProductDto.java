package com.mystarter.product.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品信息 DTO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class ProductDto implements Serializable {

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String productName;
    private String productCode;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String coverImage;
    private Integer status;
    private Integer sortOrder;
    private LocalDateTime createTime;
}
