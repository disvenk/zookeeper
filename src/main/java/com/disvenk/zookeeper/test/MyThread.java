package com.disvenk.zookeeper.test;

import com.disvenk.zookeeper.config.RedisTemplateImproveLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyThread implements Runnable{

    @Autowired
    StringRedisTemplate redisTemplate;

    private RedisTemplateImproveLock lock = new RedisTemplateImproveLock(redisTemplate,"name");

    public int ticket = 100;

    @Override
    public void run() {
        while (ticket>0)
            try {
                	//	Thread.sleep(100);
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"正在售卖第"+ticket--+"张票");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

    }
}
