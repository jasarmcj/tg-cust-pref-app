package com.tg.cust.pref.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author jasar
 *
 */
@EnableSwagger2
@SpringBootApplication
public class TgCustPrefStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgCustPrefStoreApplication.class, args);
	}

}
