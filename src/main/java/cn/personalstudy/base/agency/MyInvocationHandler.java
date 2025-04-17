package cn.personalstudy.base.agency;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理处理器
 *
 * @author: qiaohaojie
 * @date: 2025-04-17 23:06:29
 */
public class MyInvocationHandler implements InvocationHandler {

    private final Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method call...");
        Object result = method.invoke(target, args);
        System.out.println("After method call...");
        return result;
    }

    /**
     * 使用动态代理
     *
     * @param args
     */
    public static void main(String[] args) {
        MyService target = new MyServiceImpl();
        MyService proxy = (MyService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new MyInvocationHandler(target)
        );

        proxy.doSomething();
    }

}
