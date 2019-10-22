/*
package com.daishaowen.test.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(RedisClusterConfigProperties.class)
public class RedisClusterConfig {

    @Autowired
    private RedisClusterConfigProperties configProperties;

    @Bean
    public JedisCluster getJedisCluster() {
        String[] cNodes = configProperties.getCluster().get("nodes").split(",");
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        // 分割出集群节点
        for (String node : cNodes) {
            String[] hp = node.split(":");
            nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(40);
        jedisPoolConfig.setMinIdle(7);
        jedisPoolConfig.setMaxWaitMillis(10000);
        // 创建集群对象
//      JedisCluster jedisCluster = new JedisCluster(nodes,commandTimeout);
       JedisCluster jedisCluster = new JedisCluster(nodes,configProperties.getTimeout(),
                configProperties.getTimeout(),10,"123",jedisPoolConfig);
       //出现异常最大重试次数2
       // 返回值的超时时间3

        return jedisCluster;
    }

}
*/
