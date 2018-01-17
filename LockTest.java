package com.github.binarywang.wxpay.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: yzhang
 * @Date: 2018/1/17 10:05
 */
public class LockTest {


    public static void main(String[] args){
            final Data data = new Data();
            for(int i=0;i<3;i++){
                new Thread(()->{
                    for(int j=0;j<5;j++) {
                        data.set(new Random().nextInt(30));
                    }
                }).start();
            }
            for(int i = 0;i<3;i++){
                new Thread(()->{
                    for(int j=0;j<5;j++){
                        data.get();
                    }
                }).start();
            }

    }


}

class Data {
    private int data;//共享数据
    //private ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private Lock lock=new ReentrantLock();
    /*public synchronized void set(int data){
        System.out.println(Thread.currentThread().getName()+"准备写入数据");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException  e){
            e.printStackTrace();
        }
        this.data = data;
        System.out.println(Thread.currentThread().getName()+"写入"+this.data);
    }
    public synchronized void get(){
        System.out.println(Thread.currentThread().getName()+"准备读取数据");
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"读取数据"+this.data);
    }*/
    public  void set(int data){
//        readWriteLock.writeLock().lock();//获取锁
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "准备写入数据");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "写入" + this.data);
        }finally {
//            readWriteLock.writeLock().unlock();//删除锁
            lock.unlock();
        }
    }
    public  void get(){
//        readWriteLock.readLock().lock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"准备读取数据");
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"读取数据"+this.data);
        }finally {
//            readWriteLock.readLock().unlock();
            lock.unlock();
        }

    }
}
