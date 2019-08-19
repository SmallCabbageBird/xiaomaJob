package com.xiaoma.job.Excption;

import com.xiaoma.job.pojo.Result;
import com.xiaoma.job.pojo.Status;
import com.xiaoma.job.util.ResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(CheckExcption.class)
    @ResponseStatus(HttpStatus.OK)
    public Result CheckExcption(CheckExcption e) {
        return ResultUtils.error(e.getMessage());
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new Result(Status.PARAMERROR.getCode(),e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result BindException(BindException e) {
        return new Result(Status.PARAMERROR.getCode(),e.getBindingResult().getFieldError().getDefaultMessage());
    }

}
