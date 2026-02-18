package com.mystarter.product.service;

import com.mystarter.common.core.page.PageResult;
import com.mystarter.product.api.dto.CategoryDto;
import com.mystarter.product.api.dto.ProductDto;
import com.mystarter.product.api.vo.CategorySaveVo;
import com.mystarter.product.api.vo.ProductQueryVo;
import com.mystarter.product.api.vo.ProductSaveVo;

import java.util.List;

/**
 * 商品管理 Service
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
public interface ProductManageService {

    /**
     * 分页查询商品
     */
    PageResult<ProductDto> page(ProductQueryVo queryVo);

    /**
     * 保存商品
     */
    void save(ProductSaveVo saveVo);

    /**
     * 删除商品
     */
    void delete(Long id);

    /**
     * 查询分类树
     */
    List<CategoryDto> categoryTree();

    /**
     * 保存分类
     */
    void saveCategory(CategorySaveVo saveVo);

    /**
     * 删除分类
     */
    void deleteCategory(Long id);
}
