//package com.disvenk.zookeeper.config;
//
//import com.disvenk.zookeeper.test.MyThread;
//import com.disvenk.zookeeper.utils.RedisUtis;
//import net.bytebuddy.asm.Advice;
//import org.apache.zookeeper.KeeperException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import java.util.UUID;
//
//@RestController
//public class OneController {
//
//   //@Autowired
//   //ZookeeperImproveLock zookeeperImproveLock;
//
//    @Autowired
//    JedisPool jedisPool;
//
//    @Autowired
//    RedisImproveLock redisImproveLock;
//
//   @Autowired
//   RedisUtis redisUtis;
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Autowired
//   private RedisTemplateImproveLock lock;
//
//    @RequestMapping("lock1")
//    public String lock1() {
//        //KeeperErrorCode = Session expired for,这里只能使用new的方式，不能注入到spring环境
//        ZookeeperImproveLock lock   = new ZookeeperImproveLock();
//        try {
//            lock.lock();
//            String s = UUID.randomUUID().toString();
//            redisTemplate.opsForValue().set("name", s);
//            System.out.println(s);
//           //Thread.sleep(10000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            lock.unLock();
//        }
//        return "succse1";
//    }
//
//    @RequestMapping("lock2")
//    public String lock2() {
//        //RedisImproveLock lock  = new RedisImproveLock();
//        try {
//            redisImproveLock.lock();
//            String s = UUID.randomUUID().toString();
//            redisTemplate.opsForValue().set("name", s);
//            System.out.println(s);
//            //Thread.sleep(10000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            redisImproveLock.unlock();
//        }
//        return "succse2";
//    }
//
//    @RequestMapping("lock3")
//    public String lock3() {
//        String value = UUID.randomUUID().toString();
//        //String set = jedisPool.getResource().set("LOCK", value, "NX", "PX", 20000);
//        try{
//            Jedis resource = jedisPool.getResource();
//            String set =resource.set("LOCK", value);
//            //redisTemplate.opsForValue().set("LOCK", value);
//            System.out.println(set);
//            resource.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        return "succse2";
//    }
//
//    @RequestMapping("redis")
//    public Boolean redis(){
//       // Boolean aBoolean = redisUtis.setNx("name", 10l, "disvenk");
//        //redisUtis.setString("name","111");
//       // MyThread myThread = new MyThread();
//       boolean lock = this.lock.lock();
//        return lock;
//    }
//}
