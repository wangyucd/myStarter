package com.mystarter.product.infra;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystarter.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品分类 Mapper
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
}
