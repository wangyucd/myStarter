package com.mystarter.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mystarter.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品实体
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_product")
public class ProductEntity extends BaseEntity {

    private Long categoryId;
    private String productName;
    private String productCode;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String coverImage;
    private Integer status;
    private Integer sortOrder;
}
