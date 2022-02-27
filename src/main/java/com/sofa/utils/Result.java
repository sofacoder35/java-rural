package com.sofa.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
@ApiModel(value = "响应参数")
public class Result implements Serializable {

    @ApiModelProperty(value = "是否成功",dataType = "boolean")
    private boolean flag;

    @ApiModelProperty(value = "响应信息",dataType = "String")
    private String message;

    @ApiModelProperty(value = "响应数据",dataType = "Object")
    private Object data;



    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    /**
     * 响应成功
     * @param message
     * @param data
     * @return
     */
    public static Result success(String message,Object data){
        return new Result(true,message,data);
    }

    /**
     * 响应失败
     * @param message
     * @return
     */
    public static Result fail(String message){
        return new Result(false,message);
    }

    public static Result success(String message){
        return new Result(true,message);
    }
}
