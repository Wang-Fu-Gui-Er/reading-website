package com.reading.website.api.base;

import lombok.Data;

/**
 * 返回参数状态码 枚举类
 *
 * @xyang010 2019/1/10
 */
public enum  StatusCodeEnum {
    MAPPER_ERROR(100, "EXECUTE_MAPPER_ERROR"),
    PARAM_ERROR(200, "PARAM_ERROR"),
    SERVICE_ERROR(300, "SERVICE_ERROR"),
    LOGIC_ERROR(400, "LOGIC_ERROR"),
    INNER_SERVICE_ERROR(500, "INNER_SERVICE_ERROR"),
    NOT_FOUND(600, "NOT_FOUND"),
    SUCCESS(800, "SUCCESS"),
    PASSWORD_ERROR(900, "PASSWORD_ERROR");

    private Integer code;
    private String mark;

    StatusCodeEnum(Integer code, java.lang.String mark) {
        this.code = code;
        this.mark = mark;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
