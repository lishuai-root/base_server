package com.shuai.base.baseCommon.message;


import lombok.ToString;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 20:46
 * @version: 1.0
 */

@ToString(callSuper = true)
public class ResponseMessage extends Message {
    public ResponseMessage(int status, boolean success) {
        this.status = status;
        this.success = success;
    }
}
