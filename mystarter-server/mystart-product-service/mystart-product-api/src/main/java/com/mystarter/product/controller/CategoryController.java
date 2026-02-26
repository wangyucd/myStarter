package com.mystarter.product.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mystarter.common.core.result.R;
import com.mystarter.product.api.dto.CategoryDto;
import com.mystarter.product.api.vo.CategorySaveVo;
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

import java.util.List;

/**
 * 商品分类管理 Controller
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@RestController
@RequestMapping("/product/category")
@AllArgsConstructor
public class CategoryController {

    private final ProductManageService productManageService;

    /**
     * 查询分类树
     */
    @GetMapping("/tree")
    @SaCheckPermission("product:category:list")
    public R<List<CategoryDto>> tree() {
        return R.ok(productManageService.categoryTree());
    }

    /**
     * 保存分类
     */
    @PostMapping("/save")
    @SaCheckPermission("product:category:add")
    public R<Void> save(@Valid @RequestBody CategorySaveVo saveVo) {
        productManageService.saveCategory(saveVo);
        return R.ok();
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("product:category:delete")
    public R<Void> delete(@PathVariable Long id) {
        productManageService.deleteCategory(id);
        return R.ok();
    }
}
