package com.shuai.base.baseCommon.interceptor.baseImpl;

import com.shuai.base.baseCommon.message.BaseException;
import com.shuai.base.baseCommon.message.Message;
import com.shuai.base.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 17:34
 * @version: 1.0
 */

@RestControllerAdvice
@Log4j2
public class ExceptionInterceptor {

    @ExceptionHandler(value = {Exception.class})
    public Message deniedPermissionException(Exception e) {
        if (e == null) {
            return null;
        }
        log.error(e.getMessage());
        if (!(e instanceof BaseException)) {
            return MessageUtils.error();
        }
        return MessageUtils.error(e.getMessage());
    }
}
