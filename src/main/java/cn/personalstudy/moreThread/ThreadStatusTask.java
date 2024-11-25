package cn.personalstudy.moreThread;

/**
 * 线程的6种状态
 *
 * @author: qiaohaojie
 * @date: 2024-11-25 22:49:22
 */
public class ThreadStatusTask {

    public static void main(String[] args) {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "a");

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "b");

        a.start();
        b.start();

        System.out.println(a.getName() + ":" + a.getState()); // 输出？
        System.out.println(b.getName() + ":" + b.getState()); // 输出？

        /*
         * 调用start()方法将线程转换为RUNNABLE状态，CPU执行效率高的话，还未等到两个线程真正开始争夺锁，就已经打印出状态了，此时的状态为：
         * a:RUNNABLE
         * b:RUNNABLE
         *
         * CPU执行效率不高，线程a抢到了锁，线程b进入阻塞状态：
         * a:RUNNABLE
         * b:BLOCKED
         */
    }

    // 同步方法争夺锁
    private static synchronized void testMethod() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
