package com.mystarter.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mystarter.common.core.page.PageResult;
import com.mystarter.product.api.dto.CategoryDto;
import com.mystarter.product.api.dto.ProductDto;
import com.mystarter.product.api.vo.CategorySaveVo;
import com.mystarter.product.api.vo.ProductQueryVo;
import com.mystarter.product.api.vo.ProductSaveVo;
import com.mystarter.product.entity.CategoryEntity;
import com.mystarter.product.entity.ProductEntity;
import com.mystarter.product.infra.CategoryMapper;
import com.mystarter.product.infra.ProductMapper;
import com.mystarter.product.mapper.ProductDTOMapper;
import com.mystarter.product.service.ProductManageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理 Service 实现
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProductManageServiceImpl implements ProductManageService {

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public PageResult<ProductDto> page(ProductQueryVo queryVo) {
        LambdaQueryWrapper<ProductEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(queryVo.getProductName()), ProductEntity::getProductName, queryVo.getProductName());
        wrapper.eq(queryVo.getCategoryId() != null, ProductEntity::getCategoryId, queryVo.getCategoryId());
        wrapper.eq(queryVo.getStatus() != null, ProductEntity::getStatus, queryVo.getStatus());
        wrapper.orderByAsc(ProductEntity::getSortOrder);

        Page<ProductEntity> page = new Page<>(queryVo.getPageNum(), queryVo.getPageSize());
        productMapper.selectPage(page, wrapper);

        List<ProductDto> dtos = ProductDTOMapper.INSTANCE.toDtos(page.getRecords());
        return PageResult.of(page.getTotal(), dtos);
    }

    @Override
    public void save(ProductSaveVo saveVo) {
        ProductEntity entity = ProductDTOMapper.INSTANCE.toEntity(saveVo);
        if (saveVo.getId() == null) {
            productMapper.insert(entity);
        } else {
            productMapper.updateById(entity);
        }
    }

    @Override
    public void delete(Long id) {
        productMapper.deleteById(id);
    }

    @Override
    public List<CategoryDto> categoryTree() {
        List<CategoryEntity> allCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<CategoryEntity>()
                        .orderByAsc(CategoryEntity::getSortOrder)
        );
        List<CategoryDto> dtos = ProductDTOMapper.INSTANCE.toCategoryDtos(allCategories);
        return buildTree(dtos, 0L);
    }

    @Override
    public void saveCategory(CategorySaveVo saveVo) {
        CategoryEntity entity = ProductDTOMapper.INSTANCE.toCategoryEntity(saveVo);
        if (saveVo.getParentId() == null) {
            entity.setParentId(0L);
        }
        if (saveVo.getId() == null) {
            categoryMapper.insert(entity);
        } else {
            categoryMapper.updateById(entity);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryMapper.deleteById(id);
        // 删除子分类
        categoryMapper.delete(
                new LambdaQueryWrapper<CategoryEntity>()
                        .eq(CategoryEntity::getParentId, id)
        );
    }

    /**
     * 构建分类树
     */
    private List<CategoryDto> buildTree(List<CategoryDto> allCategories, Long parentId) {
        List<CategoryDto> tree = new ArrayList<>();
        for (CategoryDto category : allCategories) {
            if (parentId.equals(category.getParentId())) {
                category.setChildren(buildTree(allCategories, category.getId()));
                tree.add(category);
            }
        }
        return tree;
    }
}
