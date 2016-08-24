package cn.zjc.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * @author zhangjinci
 * @version 2016/8/24 9:36
 * @function
 */
public class ForkJoinDemo {

    public static void main(String[] args) {
        final ForkJoinPool mainPool = new ForkJoinPool();

        int len = 1000 * 1000 * 10;
        int[] array = new int[len];
        mainPool.invoke(new SortTask(array, 0, len - 1));

    }

    private static class SortTask extends RecursiveAction {

        private int[] array;
        private int fromIndex;
        private int toIndex;
        private final int chunksize = 1024;

        public SortTask(int[] array, int fromIndex, int toIndex) {

            this.array = array;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            System.out.println("array==>" + array + ";formIndex==>"+ fromIndex + ";toIndex==>" + toIndex);
        }

        @Override
        protected void compute() {

            int size = toIndex - fromIndex + 1;
            if (size < chunksize) {
                Arrays.sort(array, fromIndex, toIndex);
            } else {
                int leftSize = size / 2;
                SortTask leftTask = new SortTask(array, fromIndex, fromIndex + leftSize);
                SortTask rightTask = new SortTask(array, fromIndex + leftSize + 1, toIndex);
                invokeAll(leftTask, rightTask);
            }
        }
    }
}

