package com.mystarter.common.core.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@Getter
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BizException(String message) {
        super(message);
        this.code = 500;
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }
}
