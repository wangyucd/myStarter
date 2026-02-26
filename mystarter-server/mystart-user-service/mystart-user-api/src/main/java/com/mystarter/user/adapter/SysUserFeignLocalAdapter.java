package com.mystarter.user.adapter;

import com.mystarter.common.core.page.PageResult;
import com.mystarter.common.core.result.R;
import com.mystarter.user.api.dto.SysUserDto;
import com.mystarter.user.api.feign.SysUserFeignClient;
import com.mystarter.user.api.vo.SysUserQueryVo;
import com.mystarter.user.service.SysUserManageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 用户模块 Feign 本地适配器
 *
 * <p>单体模式下直接代理到 ManageService，避免走 HTTP 远程调用</p>
 *
 * @author wangyu
 * @date 2026-02-18 22:20:00
 */
@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "mystarter.deploy-mode", havingValue = "monolith", matchIfMissing = true)
public class SysUserFeignLocalAdapter implements SysUserFeignClient {

    private final SysUserManageService sysUserManageService;

    /**
     * 分页查询用户列表
     *
     * @param queryVo 用户查询参数
     * @return 用户分页列表
     */
    @Override
    public R<PageResult<SysUserDto>> page(SysUserQueryVo queryVo) {
        return R.ok(sysUserManageService.page(queryVo));
    }
}
