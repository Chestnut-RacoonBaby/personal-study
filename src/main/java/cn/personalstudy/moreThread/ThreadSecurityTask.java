package cn.personalstudy.moreThread;

/**
 * 线程安全问题
 *
 * @author: qiaohaojie
 * @date: 2024-12-02 22:00:22
 */
public class ThreadSecurityTask {

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        yuanZiDemo();
    }

    private static void yuanZiDemo() throws InterruptedException {
        int numThreads = 2;
        int numIncrementsPerThread = 100000;

        Thread[] threads = new Thread[numThreads];

        for (int j = 0; j < numThreads; j++) {
            threads[j] = new Thread(() -> {
                for (int k = 0; k < numIncrementsPerThread; k++) {
                    i++;
                }
            });
            threads[j].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final value of i = " + i);
        System.out.println("Expected value = " + (numThreads * numIncrementsPerThread));

        /*
         * Final value of i = 156637
         * Expected value = 200000
         *
         * i的期望值为：200000，但实际输出的值并不是200000，这就说明i++并不是一个原子操作。
         * 在多线程环境下，由于CPU的调度和缓存机制，导致i的值可能被其他线程修改，最终导致输出的结果不正确。
         */
    }

    private static void keJianDemo(){
        // 解决线程的可见性问题，使用volatile关键字
    }
}
