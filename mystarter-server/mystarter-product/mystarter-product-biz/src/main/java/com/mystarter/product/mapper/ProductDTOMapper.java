package com.mystarter.product.mapper;

import com.mystarter.product.api.dto.CategoryDto;
import com.mystarter.product.api.dto.ProductDto;
import com.mystarter.product.api.vo.CategorySaveVo;
import com.mystarter.product.api.vo.ProductSaveVo;
import com.mystarter.product.entity.CategoryEntity;
import com.mystarter.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品模块 MapStruct 转换器
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Mapper
public interface ProductDTOMapper {

    ProductDTOMapper INSTANCE = Mappers.getMapper(ProductDTOMapper.class);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    ProductEntity toEntity(ProductSaveVo vo);

    @Mapping(target = "categoryName", ignore = true)
    ProductDto toDto(ProductEntity entity);

    List<ProductDto> toDtos(List<ProductEntity> entities);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    CategoryEntity toCategoryEntity(CategorySaveVo vo);

    @Mapping(target = "children", ignore = true)
    CategoryDto toCategoryDto(CategoryEntity entity);

    List<CategoryDto> toCategoryDtos(List<CategoryEntity> entities);
}
