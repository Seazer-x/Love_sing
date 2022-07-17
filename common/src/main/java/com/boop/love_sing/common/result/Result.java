package com.boop.love_sing.common.result;

import lombok.Data;

//统一返回结果类
@Data
public class Result<T> {

    private Integer code; //状态码

    private String message; //返回状态信息（成功 失败）

    private T data; //返回数据

    public Result() {
    }

    //成功的方法
    public static<T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        if(data != null) {
            result.setData(data);
        }
        result.setResultCode(com.boop.love_sing.common.result.ResultCode.SUCCESS);
        return result;
    }
    public void setResultCode(com.boop.love_sing.common.result.ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }
    //失败的方法
    public static<T> Result<T> fail(T data) {
        Result<T> result = new Result<>();
        if(data != null) {
            result.setData(data);
        }
        result.setResultCode(com.boop.love_sing.common.result.ResultCode.ERROR);
        return result;
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
