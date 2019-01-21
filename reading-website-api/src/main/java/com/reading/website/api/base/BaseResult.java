package com.reading.website.api.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回参数
 *
 * @yx8102 2019/1/8
 */
@Data
public class BaseResult<T> implements Serializable {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 数据
     */
    private T data;

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String message;

    public BaseResult() {
    }

    private BaseResult(Boolean success, T data, int code, String message) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static <T> BaseResult<T> rightReturn (T data) {
        return new BaseResult<T>(true, data, StatusCodeEnum.SUCCESS.getCode(), "");
    }
    public static <T> BaseResult<T> rightReturn (T data, int code, String message) {
        return new BaseResult<T>(true, data, code, message);
    }

    public static <T> BaseResult<T> errorReturn (T data, int code, String message) {
        return new BaseResult<T>(false, data, code, message);
    }

}
