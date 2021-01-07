package com.example.company_lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@ServletComponentScan //добавлено для JMS
@EnableJms //добавлено для JMS
public class СompanyLab4Application {
	
	public static void main(String[] args) {
		SpringApplication.run(СompanyLab4Application.class, args);
	}


}
