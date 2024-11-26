package cn.personalstudy.moreThread;

/**
 * 线程的6种状态
 *
 * @author: qiaohaojie
 * @date: 2024-11-25 22:49:22
 */
public class ThreadStatusTask {

    public static void main(String[] args) {
        // BLOCKED与RUNNABLE状态的转换
        blockAndRunnable();
    }

    /**
     * BLOCKED与RUNNABLE状态的转换
     */
    private static void blockAndRunnable() {
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
         * a:RUNNABLE RUNNABLE(a.start())
         * b:RUNNABLE RUNNABLE(b.start())
         *
         * CPU执行效率不高，线程a抢到了锁，线程b进入阻塞状态，此时的状态为：
         * a:RUNNABLE RUNNABLE(a.start())
         * b:BLOCKED  RUNNABLE(b.start()) -> BLOCKED(未抢到锁)
         *
         * 线程a超时等待，线程b进入阻塞状态，此时的状态为：
         * a:TIMED_WAITING RUNNABLE(a.start()) -> TIMED_WAITING(Thread.sleep()) -> RUNNABLE(sleep()时间到) -> BLOCKED(未抢到锁) -> TERMINATED
         * b:BLOCKED RUNNABLE(b.start()) -> BLOCKED(未抢到锁) -> TERMINATED
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
