package com.mystarter.product.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mystarter.common.core.page.PageResult;
import com.mystarter.common.core.result.R;
import com.mystarter.product.api.dto.ProductDto;
import com.mystarter.product.api.vo.ProductQueryVo;
import com.mystarter.product.api.vo.ProductSaveVo;
import com.mystarter.product.service.ProductManageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品管理 Controller
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@RestController
@RequestMapping("/product/product")
@AllArgsConstructor
public class ProductController {

    private final ProductManageService productManageService;

    /**
     * 分页查询商品
     */
    @GetMapping("/page")
    @SaCheckPermission("product:product:list")
    public R<PageResult<ProductDto>> page(ProductQueryVo queryVo) {
        return R.ok(productManageService.page(queryVo));
    }

    /**
     * 保存商品
     */
    @PostMapping("/save")
    @SaCheckPermission("product:product:add")
    public R<Void> save(@Valid @RequestBody ProductSaveVo saveVo) {
        productManageService.save(saveVo);
        return R.ok();
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("product:product:delete")
    public R<Void> delete(@PathVariable Long id) {
        productManageService.delete(id);
        return R.ok();
    }
}
