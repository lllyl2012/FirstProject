package com.hr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages= {"com.hr.mapper"})
@EnableTransactionManagement//开启事务
public class PcProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcProjectApplication.class, args);
	}
}
