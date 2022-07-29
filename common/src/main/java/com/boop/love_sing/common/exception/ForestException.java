package com.boop.love_sing.common.exception;

import com.boop.love_sing.common.enums.ResultCode;

public class ForestException extends RuntimeException{
    private final Integer code;

    public ForestException(ResultCode resultCode){
        super(resultCode.message());
        this.code = resultCode.code();
    }
    public Integer getCode(){
        return code;
    }
}
