package com.boop.love_sing.common.handler;

import com.boop.love_sing.common.enums.ResultCode;
import com.boop.love_sing.common.exception.ForestException;
import com.boop.love_sing.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ForestException.class)
    public Result<StackTraceElement[]> forestExceptionHandler(ForestException e){
        Result<StackTraceElement[]> result = new Result<>();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        result.setData(e.getStackTrace());
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    public Result<StackTraceElement[]> validationExceptionHandler(Exception e){
        Result<StackTraceElement[]> result = new Result<>();
        result.setResultCode(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        result.setData(e.getStackTrace());
        return result;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<StackTraceElement[]> accessDeniedHandler(Exception e){
        Result<StackTraceElement[]> result = new Result<>();
        result.setResultCode(ResultCode.PERMISSION_NO_ACCESS);
        result.setData(e.getStackTrace());
        return result;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<StackTraceElement[]> accessDeniedHandler(MethodArgumentNotValidException e){
        Result<StackTraceElement[]> result = new Result<>();
        e.getBindingResult().getAllErrors().forEach((error)->{
            result.setCode(ResultCode.PARAM_IS_INVALID.code());
            result.setMessage(error.getDefaultMessage());
        });
        return result;
    }
}
