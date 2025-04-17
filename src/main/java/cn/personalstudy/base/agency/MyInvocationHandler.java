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

    // 要代理的目标对象
    private final Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 代理类的逻辑就是把所有接口方法的调用转发到切面类的invoke()方法上，然后根据反射调用目标类的方法。
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method call...");
        Object result = method.invoke(target, args);
        System.out.println("After method call...");
        return result;
    }

    /**
     * 使用动态代理
     * ① 首先通过实现InvocationHandler接口得到一个切面类
     * ② 然后利用Proxy根据目标类的类加载器、接口和切面类得到一个代理类
     * ③ 代理类的逻辑就是把所有接口方法的调用转发到切面类的invoke()方法上，然后根据反射调用目标类的方法
     *
     * @param args
     */
    public static void main(String[] args) {
        MyService target = new MyServiceImpl();
        // 得到代理类，只有运行后才会生成这个代理类
        MyService proxy = (MyService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new MyInvocationHandler(target)
        );

        proxy.doSomething();
    }

}
