package cn.zjc.thread;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjinci
 * @version 2016/8/22 20:52
 * @function
 */
public class DelayQueueTest {

    public static void main(String[] args) throws Exception{

        int num = 20;
        CountDownLatch countDownLatch =new CountDownLatch(num + 1);
        DelayQueue<Student> students = new DelayQueue<>();
        Random random =new Random();
        for (int i=0;i<num;i++){
            students.put(new Student("student" + (i+1),30+ random.nextInt(120),countDownLatch));
        }
        Thread teacherThread = new Thread(new Teacher(students));
        students.put(new EndExam(students,120,countDownLatch,teacherThread));
        teacherThread.start();
        countDownLatch.await();
        System.out.println("考试时间到，全部交卷！");
    }
}



class Student implements Runnable,Delayed{

    private String name;
    private long workTime;
    private long submitTime;
    private boolean isForce = false;
    private CountDownLatch countDownLatch;

    public Student(){}

    public Student(String name,long workTime,CountDownLatch countDownLatch){
        this.name = name;
        this.workTime = workTime;
        this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS)+System.nanoTime();
        this.countDownLatch = countDownLatch;
    }

    @Override
    public int compareTo(Delayed o) {
        // TODO Auto-generated method stub
        if(o == null || ! (o instanceof Student)) return 1;
        if(o == this) return 0;
        Student s = (Student)o;
        if (this.workTime > s.workTime) {
            return 1;
        }else if (this.workTime == s.workTime) {
            return 0;
        }else {
            return -1;
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // TODO Auto-generated method stub
        return unit.convert(submitTime - System.nanoTime(),  TimeUnit.NANOSECONDS);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (isForce) {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟"+" ,实际用时 120分钟" );
        }else {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟"+" ,实际用时 "+workTime +" 分钟");
        }
        countDownLatch.countDown();
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean isForce) {
        this.isForce = isForce;
    }
}

class EndExam extends Student {
    private DelayQueue<Student> students;
    private CountDownLatch countDownLatch;
    private Thread teacherThread;

    public EndExam(DelayQueue<Student> students,long workTime, CountDownLatch countDownLatch, Thread teacherThread) {
        super("强制收卷",workTime,countDownLatch);
        this.students = students;
        this.countDownLatch = countDownLatch;
        this.teacherThread = teacherThread;
    }

    @Override
    public void run() {
        teacherThread.interrupt();
        Student temStudent;
        for (Iterator<Student> iterator = students.iterator();iterator.hasNext();){
            temStudent = iterator.next();

            temStudent.run();
        }
    }
}

class Teacher implements Runnable{

    private DelayQueue<Student> students;

    public Teacher(DelayQueue<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        try {
            System.out.println("test start");
            while (!Thread.interrupted()){
                students.take().run();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
