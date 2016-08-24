package cn.zjc.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjinci
 * @version 2016/8/24 11:38
 * @function
 */
public class ArrayThreadPoolExecutror extends ThreadPoolExecutor {

    public ArrayThreadPoolExecutror(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
