package com.marcura.exchangerates;

import com.marcura.exchangerates.fixer.FixerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FixerProperties.class)
public class ExchangeRatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRatesApplication.class, args);
    }

}
