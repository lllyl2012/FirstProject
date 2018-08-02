package com.hr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages= {"com.hr.mapper"})
public class PcProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcProjectApplication.class, args);
	}
}
