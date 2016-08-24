package cn.zjc.thread;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * @author zhangjinci
 * @version 2016/8/22 19:32
 * @function 测试concurrent下面的线程实现方法
 */
public class TestThread {

    @Test
    public void Test1() throws Exception{
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);

    }

    @Test
    public void Test2() throws Exception{
        DelayQueue<DelayedElement> queue = new DelayQueue<>();

        DelayedElement elent = new DelayedElement("delay Queue");

        queue.put(elent);
        Thread.sleep(10 * 1000);

        DelayedElement elent1 =  queue.take();
        System.out.println(elent1.getName());
    }
}
