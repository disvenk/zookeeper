package com.disvenk.zookeeper.config;

import com.disvenk.zookeeper.utils.RedisUtis;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Component
@Slf4j
public class RedisImproveLock implements Lock {

    @Autowired
    JedisPool jedisPool;

    private static final String LOCK = "LOCK";

    private ThreadLocal<String > threadLocal = new ThreadLocal<>();

    @Override
    public void lock() {
        if(!tryLock()){
            try{
                // 短暂休眠，nano避免出现活锁
                Thread.sleep(new Random().nextInt(10)+1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            lock();
        }

    }

    @Override
    public boolean tryLock() {

        String value = UUID.randomUUID().toString();
        threadLocal.set(value);
        //EX是秒 PX是毫秒
        Jedis resource = jedisPool.getResource();
        String set =resource .set(LOCK, value, "NX", "PX", 1000);
        resource.close();//一定要归还到连接池
        System.out.println(set);
        if(set!=null && "ok".equalsIgnoreCase(set)){
            log.info(Thread.currentThread().getName()+"获取了分布式锁");
            return true;
        }
        return false;
    }

    @Override
    public void unlock() {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        String script = sb.toString();

        Object eval = jedisPool.getResource().eval(script, Arrays.asList(LOCK), Arrays.asList(threadLocal.get()));
        if(eval!=null){
            log.info(Thread.currentThread().getName()+"解锁");
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}
