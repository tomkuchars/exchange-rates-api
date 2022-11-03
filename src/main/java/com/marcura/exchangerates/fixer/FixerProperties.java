package com.marcura.exchangerates.fixer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "fixer")
public record FixerProperties(String apiUrl, String apiKey, String baseCurrency) {
}
