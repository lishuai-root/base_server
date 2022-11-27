package com.shuai.base.controller.loginAction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shuai.base.baseCommon.annotation.basePermission.BaseIgnoreInterception;
import com.shuai.base.baseCommon.annotation.basePermission.BaseRequestPermission;
import com.shuai.base.baseCommon.common.User;
import com.shuai.base.baseCommon.common.UserMessage;
import com.shuai.base.baseCommon.common.UserPermission;
import com.shuai.base.baseCommon.message.Message;
import com.shuai.base.service.userService.UserService;
import com.shuai.base.utils.MessageUtils;
import com.shuai.base.utils.fileUtils.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/18 14:00
 * @version: 1.0
 */

@Log4j2
@RestController
@RequestMapping("base")
@AllArgsConstructor
public class Login {

    UserMessage userMessage;

    UserService userService;

    @PostMapping(value = "login")
    public Message login(User user, HttpServletRequest request) {
        log.info("login user : " + user);
        Message login = userService.login(user);
        log.info(login.toString());
        return login;
    }

    @BaseIgnoreInterception(ignorePermission = UserPermission.DEFAULT_PERMISSION)
    @PostMapping(value = "register")
    public Message registerUser(User user) {
        return userService.registerUser(user);
    }

    //    @BaseIgnoreInterception
    @PostMapping("exit")
    public boolean exit(HttpServletRequest request) {
        request.getSession().invalidate();
        return true;
    }

    @PostMapping("checkUser")
    public void checkUser(String userJson) {
        JSONObject jsonObject = JSON.parseObject(userJson);
        System.out.println(jsonObject.getClass());
        System.out.println(jsonObject);
//        User user = (User) jsonObject;
//        System.out.println(user);
    }

    @PostMapping("permissionTest")
    public String permissionTest() {
        System.out.println("permissionTest");
        return "permissionTest";
    }

    @PostMapping(value = "test")
    @BaseRequestPermission
    public void test(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("method test .");
    }

    @BaseIgnoreInterception(ignorePermission = UserPermission.DEFAULT_PERMISSION)
    @PostMapping(value = "root")
    @BaseRequestPermission(baseExecutePermission = UserPermission.ROOT_PERMISSION)
    public String inspectPermissionRoot() {
        return "root";
    }

    @PostMapping(value = "admin")
    @BaseRequestPermission(baseExecutePermission = UserPermission.ADMIN_PERMISSION)
    public String inspectPermissionAdmin() {
        return "admin";
    }

    @PostMapping(value = "user")
    @BaseRequestPermission()
    public String inspectPermissionUser() {
        return "user";
    }

    @GetMapping(value = "jpg")
    public File downloadFile(HttpServletResponse response) throws IOException {
        File file = new File("src/main/resources" + File.separator + "static/images/last2.webp");
        return file;
    }

    @BaseIgnoreInterception(ignorePermission = UserPermission.DEFAULT_PERMISSION)
    @GetMapping(value = "msg")
    public Message downloadMsg() {
        File file = new File("src/main/resources" + File.separator + "static/images/last2.webp");
        return MessageUtils.ok(file);
    }

    @GetMapping(value = "data")
    public void downloadMsg(HttpServletResponse response) throws IOException {
        File file = new File("src/main/resources" + File.separator + "static/images/last2.webp");
        boolean b = FileUtils.writeFile(response.getOutputStream(), file);
        response.addHeader("Content-Length", "" + file.length());
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));

    }

    @PostMapping(value = "createTable")
    public boolean createTable() {
        userService.createTable();
        return true;
    }
}
