package com.mystarter.product.api.feign;

import com.mystarter.common.core.page.PageResult;
import com.mystarter.common.core.result.R;
import com.mystarter.product.api.dto.CategoryDto;
import com.mystarter.product.api.dto.ProductDto;
import com.mystarter.product.api.vo.ProductQueryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 商品模块 Feign 接口
 *
 * <p>供其他模块远程调用商品模块能力</p>
 *
 * @author wangyu
 * @date 2026-02-18 22:20:00
 */
@FeignClient(name = "mystarter-product", contextId = "productFeignClient")
public interface ProductFeignClient {

    /**
     * 分页查询商品列表
     *
     * @param queryVo 商品查询参数
     * @return 商品分页列表
     */
    @GetMapping("/product/product/page")
    R<PageResult<ProductDto>> page(@SpringQueryMap ProductQueryVo queryVo);

    /**
     * 查询分类树
     *
     * @return 分类树列表
     */
    @GetMapping("/product/category/tree")
    R<List<CategoryDto>> categoryTree();
}
