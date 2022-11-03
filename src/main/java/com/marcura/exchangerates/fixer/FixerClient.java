package com.marcura.exchangerates.fixer;

import com.marcura.exchangerates.fixer.model.LatestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FixerClient {

    private final FixerProperties fixerProperties;

    private final WebClient webClient;

    @Autowired
    public FixerClient(FixerProperties fixerProperties) {
        this.fixerProperties = fixerProperties;
        this.webClient = WebClient.builder()
                .baseUrl(this.fixerProperties.apiUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("apikey", this.fixerProperties.apiKey())
                .build();
    }

    public LatestResponse getLatest() {
        WebClient.ResponseSpec response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("base", fixerProperties.baseCurrency())
                        .build())
                .retrieve();
        ResponseEntity<LatestResponse> responseEntity = response.toEntity(LatestResponse.class).block(); //TODO non blocking
        return responseEntity != null ? responseEntity.getBody() : null;
    }
}
