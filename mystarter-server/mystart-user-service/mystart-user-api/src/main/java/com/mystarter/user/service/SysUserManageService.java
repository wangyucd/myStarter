package com.mystarter.user.service;

import com.mystarter.common.core.page.PageResult;
import com.mystarter.user.api.dto.LoginDto;
import com.mystarter.user.api.dto.SysUserDto;
import com.mystarter.user.api.vo.LoginVo;
import com.mystarter.user.api.vo.SysUserQueryVo;
import com.mystarter.user.api.vo.SysUserSaveVo;

/**
 * 用户管理 Service
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
public interface SysUserManageService {

    /**
     * 用户登录
     *
     * @param loginVo 登录请求
     * @return 登录信息
     */
    LoginDto login(LoginVo loginVo);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    LoginDto getUserInfo();

    /**
     * 分页查询用户
     *
     * @param queryVo 查询条件
     * @return 分页结果
     */
    PageResult<SysUserDto> page(SysUserQueryVo queryVo);

    /**
     * 新增或编辑用户
     *
     * @param saveVo 用户信息
     */
    void save(SysUserSaveVo saveVo);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void delete(Long id);
}
