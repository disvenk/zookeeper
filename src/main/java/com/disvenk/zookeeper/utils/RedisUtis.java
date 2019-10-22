package com.disvenk.zookeeper.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/* created by disvenk.dai 2015.9.20
*/

@Component
public class RedisUtis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //设置String值
    public String setString(String key,String value){
        redisTemplate.opsForValue().set(key,value);
        return key;
    }

    //多个String值的设置
    public Map mSet(Map<String,String> map){
        redisTemplate.opsForValue().multiSet(map);
        return map;
    }

    //多个String值的获取
    public List<String> mGet(List<String> keys){
        List<String> values = redisTemplate.opsForValue().multiGet(keys);
        return values;
    }

    //String值后面追加,如果key不存在则创建一个新的
    public Integer appendString(String key,String value){
        Integer append = redisTemplate.opsForValue().append(key, value);
        return append;
    }

    //设置值并获取值,返回value,只能对String有用,获取原来key键对应的值并重新赋新值,原key要存在才行
    public String getAndSet(String key,String value){
        String andSet = redisTemplate.opsForValue().getAndSet(key, value);
        return andSet;
    }

    //如果键不存在则新增,存在则不改变已经有的值。
    public Boolean setIfAbSent(String key,String value){
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value);
        return aBoolean;
    }

    //覆盖从指定位置开始的值,仅限String
    public String set(String key,String value,long offset){
        redisTemplate.opsForValue().set(key,value,offset);
        return key;
    }

    //获取String值
    public String getString(String key){
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }

    //获得String值的大小，如果值不是String类型则报错
    public Long getStringSize(String key){
        Long size = redisTemplate.opsForValue().size(key);
        return size;
    }

    //获取指定偏移量的字符
    public String  getStartAndEnd(String key,long start,long end){
        String s = redisTemplate.opsForValue().get(key, start, end);
        return s;
    }

    //按指定整数值递增，输入负数表示递减
    public Long increment(String key,long add){
        Long increment = redisTemplate.boundValueOps(key).increment(add);
        return increment;
    }

    //按指定小数值递增，输入负数表示递减
    public Double increment(String key,double add){
        Double increment = redisTemplate.boundValueOps(key).increment(add);
        return increment;
    }

    //设置Stringkey+StringValue+timeout
    public  Map<String,Object> setStringExpire(String key,String value,long timeout){
        redisTemplate.opsForValue().set(key,value,timeout);
        Map<String,Object> map = new HashMap<>();
        map.put("key",key);
        map.put("timeout",timeout);
        return map;
    }

    public void set(String key ,String value,long time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }

    //设置Object过期时间
    public Map<String,Object> setObjetExpire(String key, long timeout, TimeUnit timeUnit){
        Boolean expire = redisTemplate.expire(key, timeout, timeUnit);
        Map<String,Object> map = new HashMap<>();
        map.put("key",key);
        map.put("isSuccess",expire);
        map.put("timeout",timeout);
        map.put("timeUnit",timeUnit);
        return map;
    }

    //根据key获取过期时间
    public Long getExpire(String key){
        Long expire = redisTemplate.getExpire(key);
        return expire;
    }

    //根据key获取过期时间并换算成指定的时间
    public Long getExpireTransfor(String key,TimeUnit unit){
        Long expire = redisTemplate.getExpire(key, unit);
        return expire;
    }

    //根据key删除值
    public void delete(String key){
       redisTemplate.delete(key);
        //return delete;
    }

    //批量删除值
    public void mDel(List<String> keys){
        redisTemplate.delete(keys);

    }

    //检测key是否存在
    public Boolean exisist(String key){
        Boolean aBoolean = redisTemplate.hasKey(key);
        return aBoolean;
    }

    //setNx
    public Boolean setNx(String key,long time,String value){
        //Boolean aBoolean = redisTemplate.getConnectionFactory().getConnection().setEx(key.getBytes(), time, value.getBytes());
        // Boolean aBoolean = redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), value.getBytes());
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, "10");
        return aBoolean;
    }

}
