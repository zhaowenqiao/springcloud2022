package com.wenqiao.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonListResult<T> {
    private String code;
    private String msg;
    private List<T> dataList;

    public CommonListResult(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public  CommonListResult (List<T> dataList){
        this.code = "200";
        this.msg = "处理成功";
        this.dataList = dataList;
    }

    public static CommonListResult success(){
        CommonListResult commonListResult = new CommonListResult("200","处理成功");
        return commonListResult;
    }

    public static CommonListResult fail(){
        CommonListResult commonListResult = new CommonListResult("400","处理失败");
        return commonListResult;
    }
}
