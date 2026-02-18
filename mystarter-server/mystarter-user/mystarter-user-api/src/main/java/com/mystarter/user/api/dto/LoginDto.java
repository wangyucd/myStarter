package com.mystarter.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录响应 DTO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class LoginDto implements Serializable {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 权限码列表
     */
    private List<String> permissions;
}
