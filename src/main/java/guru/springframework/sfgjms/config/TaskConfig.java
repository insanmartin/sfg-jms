package guru.springframework.sfgjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//Enable the task scheduler
@EnableScheduling
//Combine with @EnableScheduling sets up Spring Boot to perform tasks out of a task pool
@EnableAsync
//this is a configuration class -> tells Spring to scan for methods annotated as beans
@Configuration
public class TaskConfig
{
	//this sets up a bean called taskExecutor
	@Bean
	TaskExecutor taskExecutor()
	{
		return new SimpleAsyncTaskExecutor();
	}
}
