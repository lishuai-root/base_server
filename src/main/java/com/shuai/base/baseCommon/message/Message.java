package com.shuai.base.baseCommon.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 20:44
 * @version: 1.0
 */

@Data
public abstract class Message implements Serializable {
    int status;
    boolean success;
    Object data;
}
