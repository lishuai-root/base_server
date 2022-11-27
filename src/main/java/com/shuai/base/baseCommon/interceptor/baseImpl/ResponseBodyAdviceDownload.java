package com.shuai.base.baseCommon.interceptor.baseImpl;

import com.shuai.base.baseCommon.message.Message;
import com.shuai.base.utils.fileUtils.FileUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/19 16:12
 * @version: 1.0
 */

@Order(2)
@ControllerAdvice
@Component
public class ResponseBodyAdviceDownload implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        boolean isFile = false;
        ServletOutputStream out = null;
        File file = null;
        if (body instanceof File) {
            isFile = true;
            file = (File) body;
            body = null;
        } else {
            if (body instanceof Message) {
                Message msg = (Message) body;
                if (msg.getData() instanceof File) {
                    isFile = true;
                    file = (File) msg.getData();
                    msg.setData(null);
                }
            }
        }

        if (isFile && file.exists()) {
            ServletServerHttpResponse rsp = (ServletServerHttpResponse) response;
            rsp.getServletResponse().addHeader("fileSize", file.length() + "");
            rsp.getServletResponse().addHeader("content-disposition", "attachment;fileName=" + file.getName());
            try {
                out = rsp.getServletResponse().getOutputStream();
                FileUtils.writeFile(out, file);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return body;
    }
}
