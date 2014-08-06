package com.bookstore.net;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPoolUtils {
    
    private ThreadPoolUtils(){
        
    }
    
    //初始线程个数
    private static int CORE_POOL_SIZE = 4;
    
    //最大线程个数
    private static int MAX_POOL_SIZE = 15;
    
    //线程生存时间
    private static int KEEP_ALIVE_TIME = 10000;
    
    //创建工作队列
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(
            10);
    
    //线程工厂
    private static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "myThreadPool thread:" + integer.getAndIncrement());
        }
    };
    
    private static ThreadPoolExecutor threadPool;
    
    static {
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue,
                threadFactory);
    }
    
    
    /**
     * 执行线程
     * @param runnable
     */
    public static void execute(Runnable runnable){
        threadPool.execute(runnable);
    }

}