package com.mystarter.product.mapper;

import com.mystarter.product.api.dto.CategoryDto;
import com.mystarter.product.api.dto.ProductDto;
import com.mystarter.product.api.vo.CategorySaveVo;
import com.mystarter.product.api.vo.ProductSaveVo;
import com.mystarter.product.entity.CategoryEntity;
import com.mystarter.product.entity.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-18T21:28:07+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
public class ProductDTOMapperImpl implements ProductDTOMapper {

    @Override
    public ProductEntity toEntity(ProductSaveVo vo) {
        if ( vo == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( vo.getId() );
        productEntity.setCategoryId( vo.getCategoryId() );
        productEntity.setProductName( vo.getProductName() );
        productEntity.setProductCode( vo.getProductCode() );
        productEntity.setDescription( vo.getDescription() );
        productEntity.setPrice( vo.getPrice() );
        productEntity.setStock( vo.getStock() );
        productEntity.setCoverImage( vo.getCoverImage() );
        productEntity.setStatus( vo.getStatus() );
        productEntity.setSortOrder( vo.getSortOrder() );

        return productEntity;
    }

    @Override
    public ProductDto toDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId( entity.getId() );
        productDto.setCategoryId( entity.getCategoryId() );
        productDto.setProductName( entity.getProductName() );
        productDto.setProductCode( entity.getProductCode() );
        productDto.setDescription( entity.getDescription() );
        productDto.setPrice( entity.getPrice() );
        productDto.setStock( entity.getStock() );
        productDto.setCoverImage( entity.getCoverImage() );
        productDto.setStatus( entity.getStatus() );
        productDto.setSortOrder( entity.getSortOrder() );
        productDto.setCreateTime( entity.getCreateTime() );

        return productDto;
    }

    @Override
    public List<ProductDto> toDtos(List<ProductEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( entities.size() );
        for ( ProductEntity productEntity : entities ) {
            list.add( toDto( productEntity ) );
        }

        return list;
    }

    @Override
    public CategoryEntity toCategoryEntity(CategorySaveVo vo) {
        if ( vo == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId( vo.getId() );
        categoryEntity.setParentId( vo.getParentId() );
        categoryEntity.setCategoryName( vo.getCategoryName() );
        categoryEntity.setIcon( vo.getIcon() );
        categoryEntity.setSortOrder( vo.getSortOrder() );
        categoryEntity.setStatus( vo.getStatus() );

        return categoryEntity;
    }

    @Override
    public CategoryDto toCategoryDto(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId( entity.getId() );
        categoryDto.setParentId( entity.getParentId() );
        categoryDto.setCategoryName( entity.getCategoryName() );
        categoryDto.setIcon( entity.getIcon() );
        categoryDto.setSortOrder( entity.getSortOrder() );
        categoryDto.setStatus( entity.getStatus() );

        return categoryDto;
    }

    @Override
    public List<CategoryDto> toCategoryDtos(List<CategoryEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( entities.size() );
        for ( CategoryEntity categoryEntity : entities ) {
            list.add( toCategoryDto( categoryEntity ) );
        }

        return list;
    }
}
