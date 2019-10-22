package com.disvenk.zookeeper.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(RedisClusterConfigProperties.class)
public class RedisSingleConfig {

    @Autowired
    private RedisClusterConfigProperties configProperties;

    @Bean
    public JedisPool getJedisCluster() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"47.104.19.44",6379,3000,"123456");

        jedisPoolConfig.setMaxTotal(100);//最大连接数, 默认8个
        jedisPoolConfig.setMaxIdle(40);//最大空闲连接数, 默认8个
        //jedisPoolConfig.setMinIdle(7);//最小空闲连接数, 默认0
        //jedisPoolConfig.setMaxWaitMillis(10000);//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        //jedisPoolConfig.setMinEvictableIdleTimeMillis(600000);//连接保持空闲而不被驱逐的最长时间 默认1800000毫秒(30分钟)
        //jedisPoolConfig.setNumTestsPerEvictionRun(3);//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        //jedisPoolConfig.setTimeBetweenEvictionRunsMillis(-1);//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        //jedisPoolConfig.setTestOnBorrow(true);//在获取连接的时候检查有效性, 默认false
        //jedisPoolConfig.setTestOnCreate(true);//在空闲时检查有效性, 默认false
        //jedisPoolConfig.setTestOnReturn(true);//在进行returnObject对返回的connection进行validateObject校验
        //jedisPoolConfig.setTestWhileIdle(true);//在空闲时检查有效性, 默认false
       // jedisPoolConfig.setBlockWhenExhausted();//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        //jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
       // jedisPoolConfig.setFairness();// 多个任务需要borrow连接时，阻塞时是否采用公平策略，为true时采用，按照先申请先获得的策略进行borrow操作
       // jedisPoolConfig.setJmxEnabled(true);//是否启用pool的jmx管理功能, 默认true
        //jedisPoolConfig.setSoftMinEvictableIdleTimeMillis();//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        jedisPoolConfig.setLifo(true);//是否启用后进先出, 默认true
        //jedisPoolConfig.setJmxNameBase();
        //jedisPoolConfig.setJmxNamePrefix("pool");


        return jedisPool;
    }

}

