package cn.personalstudy.moreThread;

/**
 * 线程锁
 *
 * @author: qiaohaojie
 * @date: 2024-11-27 22:19:46
 */
public class ThreadLockTask {

    public static void main(String[] args) {
//        noLock();
//        addLock();
        waitAndNotify();
    }

    /**
     * 不加锁，线程A和线程B会交替打印，线程A和线程B之间没有关系，各自独立工作，线程之间是并发执行的。
     * <p>
     * 并发：两个及两个以上的作业在同一时间段内执行。
     * 并行：两个及两个以上的作业在同一时刻执行。
     */
    private static void noLock() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread A " + i);
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread A " + i);
                }
            }
        });

        threadA.start();
        threadB.start();
    }

    /**
     * 加锁，先获取到对象锁的线程先执行，其他线程等待，直到先获取到锁的线程执行完毕，其他线程才继续执行。
     */
    private static void addLock() {
        final Object lock = new Object();
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread A " + i);
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread B " + i);
                }
            }
        });

        threadA.start();
        threadB.start();
    }

    private static void waitAndNotify() {
        Object lock = new Object();
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("Thread A:" + i);
                        // 唤醒正在等待的线程
                        lock.notify();
                        // 自己等待
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 5; i++) {
                    try {
                        System.out.println("Thread B:" + i);
                        // 唤醒正在等待的线程
                        lock.notify();
                        // 自己等待
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
