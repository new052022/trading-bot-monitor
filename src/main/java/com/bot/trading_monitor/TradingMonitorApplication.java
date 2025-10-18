package com.bot.trading_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TradingMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingMonitorApplication.class, args);
	}

}
