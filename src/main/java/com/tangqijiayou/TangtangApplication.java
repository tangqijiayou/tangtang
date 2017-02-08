package com.tangqijiayou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class TangtangApplication {

	public static void main(String[] args) {
		SpringApplication.run(TangtangApplication.class, args);
	}
}
