package cn.personalstudy.moreThread;

import java.util.stream.IntStream;

/**
 * 线程组
 *
 * @author: qiaohaojie
 * @date: 2024-11-20 22:03:08
 */
public class ThreadGroupTask {


    public static void main(String[] args) {
        // ========================================================线程组=========================================================================
        /*
         * ThreadGroup管理着它下面的Thread，ThreadGroup是一个标准的向下引用的树状结构，这样设计的原因是防止上级线程被下级线程引用而无法有效地被GC回收。
         * 线程组可以起到统一控制线程的优先级和检查线程的权限的作用。
         */
        Thread testThread = new Thread(() -> {
            System.out.println("testThread当前线程组名字：" + Thread.currentThread().getThreadGroup().getName());

            System.out.println("testThread当前线程名字：" + Thread.currentThread().getName());
        });

        testThread.start();

        System.out.println("main线程当前线程组名字：" + Thread.currentThread().getThreadGroup().getName());
        System.out.println("main线程当前线程名字：" + Thread.currentThread().getName());

        /*
         * main线程当前线程组名字：main
         * main线程当前线程名字：main
         * testThread当前线程组名字：main
         * testThread当前线程名字：Thread-0
         */


        // ========================================================线程优先级=========================================================================

        Thread t1 = new Thread();
        System.out.println("这个是默认线程优先级：" + t1.getPriority());
        t1.setPriority(10);
        System.out.println("这个是设置了线程优先级的：" + t1.getPriority());

        /*
         * 这个是默认线程优先级：5
         * 这个是设置了线程优先级的：10
         */

        /**
         * 1. Java程序中对线程所设置的优先级只是给操作系统一个建议，操作系统不一定会采纳。而真正的调用顺序，是由操作系统的线程调度算法决定的。
         * 2. Java提供一个线程调度器来监视和控制处于RUNNABLE状态的线程。
         *    线程的调度策略采用抢占式，优先级高的线程比优先级低的线程会有更大的几率优先执行。
         *    在优先级相同的情况下，按照“先到先得”的原则。每个Java程序都有一个默认的主线程，就是通过JVM启动的第一个线程main线程。
         */
        IntStream.range(1, 10).forEach(i -> {
            Thread thread = new Thread(new MyThread());
            thread.setPriority(i);
            thread.start();
        });

        /*
         * 当前执行的线程是：Thread-3，优先级：1
         * 当前执行的线程是：Thread-19，优先级：9
         * 当前执行的线程是：Thread-17，优先级：8
         * 当前执行的线程是：Thread-15，优先级：7
         * 当前执行的线程是：Thread-11，优先级：5
         * 当前执行的线程是：Thread-13，优先级：6
         * 当前执行的线程是：Thread-9，优先级：4
         * 当前执行的线程是：Thread-7，优先级：3
         * 当前执行的线程是：Thread-5，优先级：2
         */


        // ========================================================线程/线程组优先级=========================================================================
        /*
         * 如果某个线程的优先级大于线程组的优先级，那么线程的优先级就是线程组优先级，反之，线程的优先级就是线程自身的优先级。
         */
        ThreadGroup threadGroup = new ThreadGroup("testGroup");
        threadGroup.setMaxPriority(4);
        Thread t2 = new Thread(threadGroup, "testGroup.thread");
        t2.setPriority(10);
        System.out.println("线程组的优先级：" + threadGroup.getMaxPriority());
        System.out.println("线程组下的线程的优先级：" + t2.getPriority());

        /*
         * 线程组的优先级：4
         * 线程组下的线程的优先级：4
         */


        // ========================================================守护线程=========================================================================
        /*
         * 如果某线程是守护线程，那如果所有的非守护线程都结束了，这个守护线程也会自动结束。
         * 应用场景是：当所有非守护线程结束时，结束其余的子线程（守护线程）自动关闭，就免去了还要继续关闭子线程的麻烦
         */
        t2.setDaemon(true);
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.printf("当前执行的线程是：%s，优先级：%d%n",
                    Thread.currentThread().getName(),
                    Thread.currentThread().getPriority());
        }
    }
}
