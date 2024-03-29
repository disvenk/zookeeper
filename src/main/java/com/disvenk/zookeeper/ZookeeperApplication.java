package com.disvenk.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZookeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZookeeperApplication.class, args);
	}
}
