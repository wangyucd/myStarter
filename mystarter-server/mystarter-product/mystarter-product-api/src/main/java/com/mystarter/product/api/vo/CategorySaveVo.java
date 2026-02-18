package com.mystarter.product.api.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品分类保存 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class CategorySaveVo implements Serializable {

    private Long id;

    private Long parentId;

    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    private String icon;

    private Integer sortOrder;

    private Integer status;
}
