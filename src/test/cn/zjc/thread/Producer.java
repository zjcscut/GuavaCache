package cn.zjc.thread;

import java.util.concurrent.BlockingQueue;

/**
 * @author zhangjinci
 * @version 2016/8/22 19:35
 * @function
 */
public class Producer implements Runnable{

    private BlockingQueue blockingDeque =null;

    public Producer(BlockingQueue blockingDeque) {
        this.blockingDeque = blockingDeque;
    }

    @Override
    public void run() {
      try {
          blockingDeque.put("1");
          Thread.sleep(1000);
          blockingDeque.put("2");
          Thread.sleep(1000);
          blockingDeque.put("3");
      }catch (InterruptedException e){
          e.printStackTrace();
      }
    }
}
