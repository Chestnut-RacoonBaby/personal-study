package cn.personalstudy.moreThread;

import java.util.concurrent.*;

/**
 * 创建线程的三种方式
 *
 * @author: qiaohaojie
 * @date: 2024-11-18 20:51:24
 */
public class Task1 {

    /**
     * 继承Thread类，此类是Runnable的实现类：Thread implements Runnable
     */
    public static class MyThread1 extends Thread {
        @Override
        public void run() {
            System.out.println("继承Thread类：MyThread1");
        }
    }

    /**
     * 实现Runnable接口
     */
    public static class MyThread2 implements Runnable {

        @Override
        public void run() {
            System.out.println("实现Runnable接口：MyThread2");
        }
    }

    /**
     * 实现Callable接口
     */
    public static class MyThread3 implements Callable {

        @Override
        public String call() throws Exception {
            return "实现Callable接口：MyThread3";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread1 myThread1 = new MyThread1();
        // 调用start()方法后，线程才算启动
        myThread1.start();

        MyThread2 myThread2 = new MyThread2();
        Thread thread = new Thread(myThread2);
        thread.start();

        ExecutorService executor = Executors.newCachedThreadPool();
        // 此方法实际上调用的是submit(Callable<T> task)，有返回值
        Future result = executor.submit(new MyThread3());
        System.out.println(result.get());

        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask<String> futureTask = new FutureTask<String>(new MyThread3());
        // 此方法实际上调用的是submit(Runnable task)，无返回值
        //  executorService.submit(new FutureTask<String>(new MyThread3()));
        executorService.submit(futureTask);
        System.out.println(futureTask.get());

        /*
         * Callable与Runnable对比：
         * 1. Callable有返回值，而Runnable没有
         * 2. submit()方法调用时
         *    ① submit(Callable<T> task)方法有返回值，根据此方法返回的Future取get()方法
         *    ② submit(Runnable task)方法无返回值，根据FutureTask直接取get()方法
         * 3. FutureTask能够在高并发环境下确保任务只执行一次。
         */
    }
}
