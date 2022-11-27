package com.shuai.base;

import com.shuai.base.baseCommon.annotation.basePermission.EnableBaseInterceptorService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/17 23:00
 * @version: 1.0
 */

@SpringBootApplication
@MapperScan(basePackages = {"com/shuai/base/dao/"})
@Configuration
@EnableJms
@EnableScheduling
@EnableBaseInterceptorService
public class BaseServerApplication {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/cglib");
        SpringApplication.run(BaseServerApplication.class, args);
    }

//    @Bean("queue")
//    public Queue destinationQueue() {
//        return new ActiveMQQueue("topic");
//    }
//
//
//    @Bean("replayToQueue")
//    public Queue replayToQueue() {
//        return new ActiveMQQueue("replayTo");
//    }
//
//
//    @Bean("topic")
//    public Topic destinationTopic() {
//        return new ActiveMQTopic("topic");
//    }


//    /**
//     * @param activeMQConnectionFactory
//     * @return 创建topic模式的监听连接，在使用@JmsListener时用来指定从topic中获取消息
//     */
//    @Bean
//    public JmsListenerContainerFactory jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
//        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
//        defaultJmsListenerContainerFactory.setPubSubDomain(true);
//        defaultJmsListenerContainerFactory.setConnectionFactory(activeMQConnectionFactory);
//        return defaultJmsListenerContainerFactory;
//    }
//
//    /**
//     * @param activeMQConnectionFactory
//     * @return 创建queue模式的监听连接，在使用@JmsListener时用来指定从队列中获取消息
//     */
//    @Bean
//    public JmsListenerContainerFactory jmsListenerContainerQueue(ConnectionFactory activeMQConnectionFactory) {
//        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
//        defaultJmsListenerContainerFactory.setConnectionFactory(activeMQConnectionFactory);
//        return defaultJmsListenerContainerFactory;
//    }
}
