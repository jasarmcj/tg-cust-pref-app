package com.tg.cust.pref.retriever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author jasar
 *
 */
@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
public class TgCustPrefRetrieverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgCustPrefRetrieverApplication.class, args);
	}

}
