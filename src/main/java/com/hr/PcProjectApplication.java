package com.hr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@SpringBootApplication
@ComponentScan
@MapperScan(basePackages= {"com.hr.mapper"})
@EnableTransactionManagement//开启事务
@EnableAutoConfiguration
@EnableFeignClients
public class PcProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcProjectApplication.class, args);
	}
}
