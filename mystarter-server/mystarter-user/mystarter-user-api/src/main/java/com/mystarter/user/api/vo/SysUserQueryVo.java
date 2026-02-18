package com.mystarter.user.api.vo;

import com.mystarter.common.core.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户分页查询 VO
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQueryVo extends PageQuery implements Serializable {

    /**
     * 用户名（模糊搜索）
     */
    private String username;

    /**
     * 昵称（模糊搜索）
     */
    private String nickname;

    /**
     * 状态
     */
    private Integer status;
}
