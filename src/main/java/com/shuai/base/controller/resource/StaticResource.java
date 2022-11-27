package com.shuai.base.controller.resource;

import com.shuai.base.baseCommon.message.Message;
import com.shuai.base.utils.MessageUtils;
import com.shuai.base.utils.fileUtils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 17:10
 * @version: 1.0
 */

@Log4j2
@RestController
@RequestMapping(value = "static")
public class StaticResource {

    @RequestMapping(value = "*")
    public Message getStaticResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        File file = new File("src/main/resources" + File.separator + uri);
        log.info("get static source : " + file.getAbsolutePath());
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            if (!FileUtils.writeFile(out, file)) {
                return MessageUtils.error("static source output error!");
            }
            int index = uri.lastIndexOf(".");
            String contentType = FileUtils.getContentType(uri.substring(index));
            response.setContentType(contentType);
            return MessageUtils.ok();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                log.error("I/O close error .");
            }
        }
    }

}
