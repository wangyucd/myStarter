package com.mystarter.user.api.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户新增/编辑 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
public class SysUserSaveVo implements Serializable {

    /**
     * 主键（编辑时传入）
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码（新增时必填）
     */
    private String password;

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
     * 状态：0-禁用 1-启用
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    private List<Long> roleIds;
}
