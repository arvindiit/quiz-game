package com.arvind.game.quiz.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean(name = "daemonThreadPoolTaskExecutor")
	public Executor daemonThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		ThreadFactory threadFactory =  (Runnable r) -> { Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		};
		threadPoolTaskExecutor.setThreadFactory(threadFactory);
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}
}
