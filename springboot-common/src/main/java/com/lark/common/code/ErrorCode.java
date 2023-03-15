package com.lark.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 状态码
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    SUCCESS(0, "success"),
    ERROR(1, "系统异常"),
    INCOMPLETE_PARAM(2, "参数不完整"),
    INVALID_TOKEN(3, "无效的令牌"),
    FORBIDDEN(4, "没有权限访问资源"),
    VALIDATE_FAILED(5, "参数校验失败"),
    ;

    private Integer code;
    private String message;
}
