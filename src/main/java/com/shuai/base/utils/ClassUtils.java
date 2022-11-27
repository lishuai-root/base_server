package com.shuai.base.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: LISHUAI
 * @createDate: 2022/6/21 21:56
 * @version: 1.0
 */

public class ClassUtils {

    public static Method getDeclaredMethod(Class<?> cur, String methodName, Class<?>[] paramTypes) throws NoSuchMethodException {
        Method method = null;
        while (method == null && cur != null) {
            try {
                method = cur.getDeclaredMethod(methodName, paramTypes);
            } catch (NoSuchMethodException e) {

            }
            cur = cur.getSuperclass();
        }
        return method;
    }

    public static Method getAnnotationMethod(Class<?> ac, String methodName, Class<?>[] paramTypes, Class aClass) {
        Method method = null;
        while (method == null && ac != null) {
            try {
                method = ac.getDeclaredMethod(methodName, paramTypes);
                if (method.getAnnotation(aClass) == null) {
                    method = null;
                }
            } catch (NoSuchMethodException e) {

            }
            ac = ac.getSuperclass();
        }
        return method;
    }

    public static Map<Method, Annotation> getDeclaredMethodsByAnnotation(Class<?> clazz, Class annotationClazz) {
        Map<Method, Annotation> map = new HashMap<>();
        LinkedList<Class<?>> queue = new LinkedList<>();
        queue.add(clazz);

        while (!queue.isEmpty()) {
            clazz = queue.removeFirst();
            if (clazz.getName().startsWith("java")) {
                break;
            }
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Annotation annotation = method.getAnnotation(annotationClazz);
                if (annotation != null && !map.containsKey(method)) {
                    map.put(method, annotation);
                }
            }
            queue.addLast(clazz.getSuperclass());
            queue.addAll(Arrays.asList(clazz.getInterfaces()));
        }
        return map;
    }

    private static String error(String name, Class<?>[] argTypes) {
        return getName(name) + '.' + name +
                ((argTypes == null || argTypes.length == 0) ?
                        "()" :
                        Arrays.stream(argTypes)
                                .map(c -> c == null ? "null" : c.getName())
                                .collect(Collectors.joining(",", "(", ")")));
    }

    public static String getName(String name) {
        return name == null ? "" : name;
    }

}
