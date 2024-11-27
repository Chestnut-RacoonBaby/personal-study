### 一、锁与同步

在Java中，锁的概念都是基于对象的，所有又称之为“对象锁”。**一个锁同一时间只能被一个线程持有。**

那么，什么是同步呢？

可以理解为：线程同步是线程之间按照一定的顺序执行。为了达到线程同步，可以使用锁来实现。

### 二、等待/通知机制

基于锁的方式，线程需要不断地尝试获取锁，如果失败了，再继续尝试，这可能会耗费服务器资源。

而等待/通知机制是另一种方式，基于Object类的wait()方法和notify()、notifyAll()方法。

注意：这里的等待/通知机制使用的是同一个对象锁，如果两个线程使用的是不同的对象锁，那么它们之间是不能用等待/通知机制通信的。


### 三、信号量

主要是基于volatile关键字实现的信号量通信。使用了volatile变量需要进行原子操作。

有如下需求：让线程A输出0，然后线程B输出1，再然后线程A输出2…以此类推。

```java
public class Signal {
    private static volatile int signal = 0;

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            while (signal < 5) {
                if (signal % 2 == 0) {
                    System.out.println("threadA: " + signal);
                    signal++;
                }
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            while (signal < 5) {
                if (signal % 2 == 1) {
                    System.out.println("threadB: " + signal);
                    signal = signal + 1;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(1000);
        new Thread(new ThreadB()).start();
    }
}
```


### 四、管道

管道是基于“管道流”的通信方式。JDK提供了PipedWriter、PipedReader、PipedOutputStream、 PipedInputStream。

其中，前面两个是基于字符的，后面两个是基于字节流的。









