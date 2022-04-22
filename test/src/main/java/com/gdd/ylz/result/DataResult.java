package com.gdd.ylz.result;


import com.gdd.ylz.common.exception.code.BaseResponseCode;
import com.gdd.ylz.common.exception.code.ResponseCodeInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: DataResult
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
@Data
public class DataResult<T> {
    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码")
    private int code;

    /**
     * 响应提示语
     */
    @ApiModelProperty(value = "响应提示语")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    public boolean hasError(){
        return this.code!=200;
    }
    public DataResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
        this.message=null;
    }

    public DataResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public DataResult() {
        this.code= BaseResponseCode.SUCCESS.getCode();
        this.message=BaseResponseCode.SUCCESS.getMessage();
        this.data=null;
    }

    public DataResult(T data) {
        this.data = data;
        this.code=BaseResponseCode.SUCCESS.getCode();
        this.message=BaseResponseCode.SUCCESS.getMessage();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.message = responseCodeInterface.getMessage();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.message = responseCodeInterface.getMessage();
    }
    /**
     * 操作成功 data为null
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param
     * @return       com.xh.lesson.utils.DataResult<T>
     * @throws
     */
    public static DataResult success(){
        return new DataResult();
    }
    /**
     * 操作成功 data 不为null
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param data
     * @return       com.xh.lesson.utils.DataResult<T>
     * @throws
     */
    public static <T>DataResult success(T data){
        return new DataResult(data);
    }
    /**
     * 自定义 返回操作 data 可控
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param code
     * @param message
     * @param data
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static <T>DataResult getResult(int code,String message,T data){
        return new DataResult(code,message,data);
    }
    /**
     *  自定义返回  data为null
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param code
     * @param message
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static DataResult getResult(int code,String message){
        return new DataResult(code,message);
    }
    /**
     * 自定义返回 入参一般是异常code枚举 data为空
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param responseCode
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static DataResult getResult(BaseResponseCode responseCode){
        return new DataResult(responseCode);
    }
    /**
     * 自定义返回 入参一般是异常code枚举 data 可控
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param responseCode
     * @param data
     * @return       com.xh.lesson.utils.DataResult
     * @throws
     */
    public static <T>DataResult getResult(BaseResponseCode responseCode, T data){

        return new DataResult(responseCode,data);
    }

    public static DataResult error(BaseResponseCode responseCode){
        return new DataResult(responseCode);
    }

    public static DataResult error(){
        return new DataResult(-1,"操作失败");
    }
}
