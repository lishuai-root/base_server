package com.shuai.base;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
class BaseServerApplicationTests {

    @Test
    void contextLoads() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println(Boolean.class.getConstructor().newInstance());
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

    }

}
