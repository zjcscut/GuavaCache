package cn.zjc.thread;

import java.util.concurrent.BlockingQueue;

/**
 * @author zhangjinci
 * @version 2016/8/22 19:38
 * @function
 */
public class Consumer implements Runnable{

    private BlockingQueue blockingDeque = null;

    public Consumer(BlockingQueue blockingDeque) {
        this.blockingDeque = blockingDeque;
    }

    @Override
    public void run(){
        try {
            System.out.println(blockingDeque.take());
            System.out.println(blockingDeque.take());
            System.out.println(blockingDeque.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
