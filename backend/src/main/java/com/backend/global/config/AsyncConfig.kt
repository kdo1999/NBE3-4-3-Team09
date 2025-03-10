package com.backend.global.config;

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfig {

	@Bean(name = ["threadPoolTaskExecutor"])
	fun getAsyncExecutor(): Executor {
		val executor = ThreadPoolTaskExecutor()
		//스레드 풀 기본 사이즈 설정
		executor.corePoolSize = 3
		//대기열이 가득차면 추가로 사용할 스레드 최대 사이즈 설정
		executor.maxPoolSize = 10
		//corePoolSize가 가득 찬 상태에서 대기시킬 작업 개수
		executor.queueCapacity = 500
		//스레드 prefix
		executor.setThreadNamePrefix("Executor-")
		executor.initialize()
		return executor;
	}
}