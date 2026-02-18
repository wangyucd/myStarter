package com.mystarter.product.infra;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystarter.product.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品 Mapper
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductEntity> {
}
