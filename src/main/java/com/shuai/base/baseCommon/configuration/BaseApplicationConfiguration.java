package com.shuai.base.baseCommon.configuration;

import com.shuai.base.utils.TimeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/7/31 20:24
 * @version: 1.0
 */

@Configuration
public class BaseApplicationConfiguration {

    @PostConstruct
    public void startApplication() {
        System.out.println("系统启动 : " + TimeUtils.getDate(System.currentTimeMillis()));
    }

    @Scheduled(initialDelay = 9, fixedDelayString = "${fixedTime}")
    public void refreshApplication() {
        System.out.println("系统刷新 : " + TimeUtils.getDate(System.currentTimeMillis()));
    }

    @PreDestroy
    public void closeApplication() {
        System.out.println("系统关闭 : " + TimeUtils.getDate(System.currentTimeMillis()));
    }
}
