package com.reading.website.api.base;

import lombok.Data;

/**
 * 返回参数状态码 枚举类
 *
 * @xyang010 2019/1/10
 */
public enum  StatusCodeEnum {
    SUCCESS(1001, "SUCCESS"),
    MAPPER_ERROR(1002, "EXECUTE_MAPPER_ERROR"),
    PARAM_ERROR(1003, "PARAM_ERROR"),
    SERVICE_ERROR(1004, "SERVICE_ERROR"),
    LOGIC_ERROR(1005, "LOGIC_ERROR"),
    INNER_SERVICE_ERROR(1006, "INNER_SERVICE_ERROR"),
    NOT_FOUND(1007, "NOT_FOUND"),
    PASSWORD_ERROR(1008, "PASSWORD_ERROR"),
    EMAIL_ERROR(1009, "EMAIL_ERROR"),
    FILE_IS_EMPTY(1010, "FILE_IS_EMPTY"),
    FILE_UPLOAD_ERROR(1011, "FILE_UPLOAD_ERROR"),
    FILE_DOWNLOAD_ERROR(1012, "FILE_DOWNLOAD_ERROR"),
    FILE_PATH_NOT_EXIST(1013, "FILE_PATH_NOT_EXIST"),
    STREAM_CLOSE_ERROR(1014, "STREAM_CLOSE_ERROR");

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
