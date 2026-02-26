package com.mystarter.product.adapter;

import com.mystarter.common.core.page.PageResult;
import com.mystarter.common.core.result.R;
import com.mystarter.product.api.dto.CategoryDto;
import com.mystarter.product.api.dto.ProductDto;
import com.mystarter.product.api.feign.ProductFeignClient;
import com.mystarter.product.api.vo.ProductQueryVo;
import com.mystarter.product.service.ProductManageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品模块 Feign 本地适配器
 *
 * <p>单体模式下直接代理到 ManageService，避免走 HTTP 远程调用</p>
 *
 * @author wangyu
 * @date 2026-02-18 22:20:00
 */
@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "mystarter.deploy-mode", havingValue = "monolith", matchIfMissing = true)
public class ProductFeignLocalAdapter implements ProductFeignClient {

    private final ProductManageService productManageService;

    /**
     * 分页查询商品列表
     *
     * @param queryVo 商品查询参数
     * @return 商品分页列表
     */
    @Override
    public R<PageResult<ProductDto>> page(ProductQueryVo queryVo) {
        return R.ok(productManageService.page(queryVo));
    }

    /**
     * 查询分类树
     *
     * @return 分类树列表
     */
    @Override
    public R<List<CategoryDto>> categoryTree() {
        return R.ok(productManageService.categoryTree());
    }
}
