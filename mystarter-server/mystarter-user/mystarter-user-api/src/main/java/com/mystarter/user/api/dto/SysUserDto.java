package com.mystarter.user.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息 DTO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class SysUserDto implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 角色名称列表
     */
    private List<String> roleNames;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
