package cn.personalstudy.moreThread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道
 *
 * @author: qiaohaojie
 * @date: 2024-11-27 22:57:00
 */
public class ThreadPipeTask {

    private static PipedReader pipedReader;
    private static PipedWriter pipedWriter;

    private static void readerPipe() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is reader");
                int receive = 0;
                try {
                    while ((receive = pipedReader.read()) != -1) {
                        System.out.println((char) receive);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private static void writerPipe() throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("this is writer");
                try {
                    pipedWriter.write("test");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        pipedWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        pipedReader = new PipedReader();
        pipedWriter = new PipedWriter();
        pipedWriter.connect(pipedReader);

        readerPipe();
        Thread.sleep(1000);
        writerPipe();
    }
}
