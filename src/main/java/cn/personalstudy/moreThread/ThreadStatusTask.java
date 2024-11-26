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
//        blockAndRunnable();
        // WAITING与RUNNABLE状态的转换 - Object.wait()
//        waitingAndRunnableWithObject();
        // WAITING与RUNNABLE状态的转换 - Thread.join()
//        waitingAndRunnableWithJoin();
        // TIMED_WAITING与RUNNABLE状态的转换
        timedWaitingAndRunnableWithJoin();
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
}
