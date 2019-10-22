package com.disvenk.zookeeper.controller;

import com.disvenk.zookeeper.redis.JedisClientPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

/**
 * Created by disvenk.dai on 2018-08-13 10:23
 */

//redis乐观锁实现秒杀的的场景，一般要结合令牌一起使用，拿到令牌才能秒杀减库存
@RestController
public class RedisController {

    @Autowired
    JedisClientPool jedisClientPool;
    @Autowired
    JedisPool jedisPool;

    @RequestMapping("redis")
    public String set(){
        Jedis jedis1 = jedisPool.getResource();
        String disvenk = jedis1.lpop("disvenk");

        jedis1.close();
        if(disvenk!=null && !"".equals(disvenk)){
            Jedis jedis = jedisPool.getResource();
            jedis.watch("hello");
            Transaction multi = jedis.multi();
            multi.decr("hello");
                List<Object> exec = multi.exec();
                System.out.println(exec);
                if(exec.size()!=0){
                    Integer o = Integer.parseInt(exec.get(0).toString());
                    if(o>0) {
                        System.out.println(Thread.currentThread().getName() + "秒杀成功");
                    }else if(o==0){
                        System.out.println("商品已经卖完啦");
                    }else {
                        jedis.incr("hello");
                        System.out.println("商品已经卖完啦");
                    }
                    o=null; //help GC
                }
                jedis.close();
        }else {
            System.out.println("商品已经卖完啦");
        }

        return UUID.randomUUID().toString();
    }

    @RequestMapping("redis2")
    public String set2() {
        Jedis jedis = jedisPool.getResource();
        String disvenk = jedis.lpop("disvenk");
        if (disvenk != null && !"".equals(disvenk)) {
            System.out.println("秒杀成功");
            Long hello = jedis.decr("hello");
        }else {
            System.out.println("卖完啦");
        }
        jedis.close();
        return UUID.randomUUID().toString();
    }

}
