package test;

import io.MyClassLoad;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        String path = "/Users/pilgrim/Desktop/Week01/TestDemo/src/Hello.xlass";
        MyClassLoad myClassLoad = new MyClassLoad(path);

        Class<?> aClass = myClassLoad.findClass("Hello");

        Method method = aClass.getDeclaredMethod("hello");
        method.setAccessible(true);
        method.invoke(aClass.newInstance());
    }
}
