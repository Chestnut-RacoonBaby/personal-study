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
        // WAITING与RUNNABLE状态的转换 - Object.wait()
        waitingAndRunnableWithObject();
        // WAITING与RUNNABLE状态的转换 - Thread.join()
        waitingAndRunnableWithJoin();
        // TIMED_WAITING与RUNNABLE状态的转换
        timedWaitingAndRunnableWithJoin();
        // 线程中断
        terminated();
    }

    /**
     * ① BLOCKED与RUNNABLE状态的转换
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


    /**
     * ② WAITING与RUNNABLE状态的转换 - Object.wait()
     */
    private static void waitingAndRunnableWithObject() {
        Object object = new Object();
        try {
            Thread a = new Thread(() -> {
                // 当前线程必须持有对象的锁
                synchronized (object) {
                    System.out.println("a抢到锁");
                    try {
                        System.out.println("a释放锁");
                        object.wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("a执行完");
                }
            });

            Thread b = new Thread(() -> {
                // 当前线程必须持有对象的锁
                synchronized (object) {
                    System.out.println("b抢到锁");
                    try {
                        System.out.println("b释放锁");
                        object.wait(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("b执行完");
                }
            });


            a.start();
            b.start();

            System.out.println(a.getName() + ":" + a.getState()); // 输出？
            System.out.println(b.getName() + ":" + b.getState()); // 输出？

            /*
             * a抢到锁
             * a释放锁
             * Thread-0:RUNNABLE
             * b抢到锁
             * b释放锁
             * Thread-1:BLOCKED
             * b执行完
             * a执行完
             *
             * 1. 调用wait()方法之前线程必须持有对象的锁(synchronized (object))；
             * 2. 调用wait()方法时，会释放当前的锁，直到有其他线程调用notify()/notifyAll()方法唤醒等待锁的线程，线程才能重新进入RUNNABLE状态；
             * 注意：其他线程调用notify()方法只会唤醒单个等待锁的线程，如果有多个线程都在等待这个锁的话，唤醒的线程不确定。
             * 同样，调用notifyAll()方法唤醒所有等待锁的线程之后，也不一定会马上把时间片分给刚才放弃锁的那个线程，具体要看系统的调度。
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ② WAITING与RUNNABLE状态的转换 - Thread.join()
     */
    private static void waitingAndRunnableWithJoin() {
        try {
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
            a.join();
            b.start();

            System.out.println(a.getName() + ":" + a.getState()); // 输出？
            System.out.println(b.getName() + ":" + b.getState()); // 输出？

            /*
             * a:TERMINATED  线程启动之后立马调用了join()方法，main线程会等到a线程执行完毕，所以状态为TERMINATED
             * b:TIMED_WAITING/RUNNABLE(b.start()) 这里可能是RUNNABLE(尚未进入同步方法)，也可能是TIMED_WAITING(进入了同步方法等待锁)
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ③ TIMED_WAITING与RUNNABLE状态的转换
     * Thread.sleep(long)：使当前线程睡眠指定时间。这里的睡眠指的是暂时使线程停止执行，并不会释放锁，时间到后，重新进入RUNNABLE状态。
     * Object.wait(long)：使当前线程等待指定时间，时间到后会自动唤醒，拥有去争夺锁的资格。
     * Thread.join(long)：使当前线程等待指定时间，并且使线程进入TIMED_WAITING状态。
     */
    private static void timedWaitingAndRunnableWithJoin() {
        try {
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
            a.join(1000L);
            b.start();

            System.out.println(a.getName() + ":" + a.getState()); // 输出？
            System.out.println(b.getName() + ":" + b.getState()); // 输出？

            /*
             * a:TIMED_WAITING
             * b:BLOCKED/RUNNABLE
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void terminated() {
        /*
         * 在某些情况下，我们在线程启动后发现并不需要它继续执行下去时，需要中断线程。目前在 Java 里还没有安全方法来直接停止线程，但是 Java 提供了线程中断机制来处理需要中断线程的情况。
         *
         * 线程中断机制是一种协作机制。需要注意，通过中断操作并不能直接终止一个线程，而是通知需要被中断的线程自行处理。
         *
         * 简单介绍下 Thread 类里提供的关于线程中断的几个方法：
         * Thread.interrupt()：中断线程。这里的中断线程并不会立即停止线程，而是设置线程的中断状态为 true（默认是 false）；
         * Thread.isInterrupted()：测试当前线程是否被中断；
         * Thread.interrupted()：检测当前线程是否被中断，与 isInterrupted() 方法不同的是，这个方法如果发现当前线程被中断，会清除线程的中断状态。
         *
         * 在线程中断机制里，当其他线程通知需要被中断的线程后，线程中断的状态被设置为 true，但是具体被要求中断的线程要怎么处理，完全由被中断线程自己决定，可以在合适的时机中断请求，也可以完全不处理继续执行下去。
         */
    }
}
