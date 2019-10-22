package com.disvenk.zookeeper.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix="springs.redis")
public class RedisClusterConfigProperties {

    //private Map<String, String> cluster;

    private String cluster;

    private int timeout;

    private int maxIdle;

    private long maxWaitMillis;

//    public Map<String, String> getCluster() {
//        return cluster;
//    }
//
//    public void setCluster(Map<String, String> cluster) {
//        this.cluster = cluster;
//    }


    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }



}

