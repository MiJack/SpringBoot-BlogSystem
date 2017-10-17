package com.mijack.sbbs.utils;

import com.mijack.sbbs.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Mr.Yuan
 * @since 2017/10/10
 */
public class AssertUtils {
    public static void notNoll(Object o, Class<? extends RuntimeException> clazz, String msg) {
        if (o != null) {
            return;
        }
        try {
            Constructor<? extends RuntimeException> constructor = clazz.getConstructor(String.class);
            RuntimeException runtimeException = constructor.newInstance(msg);
            throw runtimeException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}