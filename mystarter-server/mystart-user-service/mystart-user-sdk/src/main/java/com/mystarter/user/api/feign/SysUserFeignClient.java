package com.mystarter.user.api.feign;

import com.mystarter.common.core.page.PageResult;
import com.mystarter.common.core.result.R;
import com.mystarter.user.api.dto.SysUserDto;
import com.mystarter.user.api.vo.SysUserQueryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户模块 Feign 接口
 *
 * <p>供其他模块远程调用用户模块能力</p>
 *
 * @author wangyu
 * @date 2026-02-18 22:20:00
 */
@FeignClient(name = "mystarter-user", contextId = "sysUserFeignClient")
public interface SysUserFeignClient {

    /**
     * 分页查询用户列表
     *
     * @param queryVo 用户查询参数
     * @return 用户分页列表
     */
    @GetMapping("/user/sysUser/page")
    R<PageResult<SysUserDto>> page(@SpringQueryMap SysUserQueryVo queryVo);
}
