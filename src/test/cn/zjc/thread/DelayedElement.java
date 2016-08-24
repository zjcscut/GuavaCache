package cn.zjc.thread;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjinci
 * @version 2016/8/22 19:44
 * @function
 */
public class DelayedElement implements Delayed{

    private String name;

    public DelayedElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.toSeconds(5);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
