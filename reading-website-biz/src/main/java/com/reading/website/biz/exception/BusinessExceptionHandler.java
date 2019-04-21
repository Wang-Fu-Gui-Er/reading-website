package com.reading.website.biz.exception;

import com.reading.website.api.base.BaseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 业务异常处理类
 *
 * @xyang010 2019/4/21x
 */
@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public BaseResult<Void> baseException(BusinessException ex) {
        return BaseResult.errorReturn(ex.getCode(), ex.getMessage());
    }
}
