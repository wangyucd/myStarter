package com.mystarter.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mystarter.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分类实体
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_category")
public class CategoryEntity extends BaseEntity {

    private Long parentId;
    private String categoryName;
    private String icon;
    private Integer sortOrder;
    private Integer status;
}
