package com.mystarter.user.api.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录请求 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class LoginVo implements Serializable {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
