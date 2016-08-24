package cn.zjc.concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjinci
 * @version 2016/8/24 11:19
 * @function
 */
public class ThreadPoolTest {

    @Test
    public void Test1() throws Exception {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(50);
        ArrayThreadPoolExecutror executror = new ArrayThreadPoolExecutror(50, 50, 0L, TimeUnit.MILLISECONDS, queue);

        for (int i = 0; i < 101; i++) {
//            System.out.print(i + "==>");
            executror.submit(() -> System.out.println(Thread.currentThread().getName()));
        }

//
//        executror.submit(() -> System.out.println("2"+ Thread.currentThread().getName()));
//
//        executror.submit(() -> System.out.println("3"+ Thread.currentThread().getName()));
//
//        executror.submit(() -> System.out.println("4"+ Thread.currentThread().getName()));
//
//
//        executror.submit(() -> System.out.println("5"+ Thread.currentThread().getName()));
//        executror.submit(() -> System.out.println("6"));


        System.in.read();
    }
}
