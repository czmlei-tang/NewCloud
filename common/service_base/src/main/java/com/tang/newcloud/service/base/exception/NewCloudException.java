package com.tang.newcloud.service.base.exception;

import com.tang.newcloud.common.base.result.ResultCodeEnum;
import lombok.Data;

@Data
public class NewCloudException extends RuntimeException {

    //状态码
    private Integer code;

    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public NewCloudException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public NewCloudException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "NewCloudException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
