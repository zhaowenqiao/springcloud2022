package com.wenqiao.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private String code;
    private String msg;
    private T data;

    public CommonResult(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CommonResult(T data){
        this.code = "200";
        this.msg = "处理成功";
        this.data = data;
    }

    public static CommonResult success(){
        CommonResult commonResult = new CommonResult("200","处理成功");
        return commonResult;
    }

    public static CommonResult fail(){
        CommonResult commonResult = new CommonResult("400","处理失败");
        return commonResult;
    }
}
