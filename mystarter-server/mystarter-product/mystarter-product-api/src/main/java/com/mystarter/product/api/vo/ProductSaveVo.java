package com.mystarter.product.api.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品保存 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class ProductSaveVo implements Serializable {

    private Long id;

    private Long categoryId;

    @NotBlank(message = "商品名称不能为空")
    private String productName;

    private String productCode;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String coverImage;

    private Integer status;

    private Integer sortOrder;
}
