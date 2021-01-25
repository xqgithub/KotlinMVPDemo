package com.example.baselibrary.utils;

import com.example.baselibrary.constants.ConfigConstants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 */
public class ReflectionUtils {

    private volatile static ReflectionUtils instance;


    public static ReflectionUtils getInstance() {
        if (instance == null) {
            synchronized (ReflectionUtils.class) {
                if (instance == null) {
                    instance = new ReflectionUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 得到该类
     */

    public Class<?> getMyClass(String name) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(ConfigConstants.TAG_ALL, e.getMessage());
        }
        return aClass;
    }


    /**
     * 获得反射类所有的构造
     */
    public List<String> showConstructors(Class<?> aClass) {
        List<String> names = null;
        try {
            Constructor[] constructors = aClass.getDeclaredConstructors();
            if (constructors.length > 0) {
                names = new ArrayList<>();
                for (Constructor constructor : constructors) {
                    names.add(constructor.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(ConfigConstants.TAG_ALL, e.getMessage());
        }
        return names;
    }


    /**
     * 获得反射类所有的方法
     */
    public List<String> showMethods(String name, Class<?> aClass) {
        List<String> names = null;
        try {
            Method[] methods = aClass.getDeclaredMethods();
            if (methods.length > 0) {
                names = new ArrayList<>();
                for (Method method : methods) {
                    names.add(method.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(ConfigConstants.TAG_ALL, e.getMessage());
        }

        return names;
    }

    /**
     * 获得反射类所有的属性
     *
     * @param name
     * @return
     */
    public List<String> showFields(String name, Class<?> aClass) {
        List<String> names = null;
        try {
            Field[] fields = aClass.getDeclaredFields();
            if (fields.length > 0) {
                names = new ArrayList<>();
                for (Field field : fields) {
                    names.add(field.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(ConfigConstants.TAG_ALL, e.getMessage());
        }
        return names;
    }

    /**
     * 属性设置值
     *
     * @param aClass       反射的当前类
     * @param constructor  构造
     * @param fieldName    要设置的属性字段
     * @param value        要设置的值
     * @param isAccessible 是否允许修改私有
     */
    public void setField(Class<?> aClass, Object constructor, String fieldName, Object value, boolean isAccessible) {
        try {
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(isAccessible);
            field.set(constructor, value);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(ConfigConstants.TAG_ALL, e.getMessage());
        }
    }

    /**
     * 获取属性的值
     *
     * @param aClass       反射的当前类
     * @param constructor  构造
     * @param fieldName    要获取的字段
     * @param isAccessible 是否允许修改私有
     * @return
     */
    public Object getField(Class<?> aClass, Object constructor, String fieldName, boolean isAccessible) {
        Object o = null;
        try {
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(isAccessible);
            o = field.get(constructor);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(ConfigConstants.TAG_ALL, e.getMessage());
        }
        return o;
    }

    /**
     * @param aClass
     * @param constructor
     * @param methodName   要执行的方法名
     * @param isAccessible
     */
    public Object runMethod(Class<?> aClass, Object constructor, String methodName, boolean isAccessible) {
        Object result = null;
        try {
            Method method = aClass.getDeclaredMethod(methodName);
            method.setAccessible(isAccessible);
            result = method.invoke(constructor);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(ConfigConstants.TAG_ALL, e.getMessage());
        }
        return result;
    }
}
