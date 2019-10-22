package com.disvenk.zookeeper;

import com.disvenk.zookeeper.test.MyThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperApplicationTests {
	public static void main(String[] args) {
		MyThread myThread = new MyThread();
		Thread thread1 = new Thread(myThread,"窗口1");
		Thread thread2 = new Thread(myThread,"窗口2");
		Thread thread3 = new Thread(myThread,"窗口3");
		thread1.start();
		thread2.start();
		thread3.start();
	}

	@Test
	public void contextLoads() {
		MyThread myThread = new MyThread();
		Thread thread1 = new Thread(myThread,"窗口1");
		Thread thread2 = new Thread(myThread,"窗口2");
		Thread thread3 = new Thread(myThread,"窗口3");
		thread1.start();
		thread2.start();
		thread3.start();
	}

}



