package com.app.exchangerates.reactive;

import java.time.Duration;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.app.exchangerates.db.rates.RatesRepository;
import com.app.exchangerates.db.rates.model.Rates;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ExchangeHandler {

	private final RatesRepository ratesRepository;

	@Autowired
	public ExchangeHandler(RatesRepository ratesRepository) {
		this.ratesRepository = ratesRepository;
	}

	public Mono<ServerResponse> events(ServerRequest request) {
		Flux<Long> interval = Flux.interval(Duration.ofMillis(20));
		Flux<Rates> ratesEvents = Mono.just(ratesRepository.findAll().stream()
				.limit(500)
				.collect(Collectors.toList())) // TODO: use R2DBC
				.flatMapMany(Flux::fromIterable);
		Flux<Rates> zipped = Flux.zip(ratesEvents, interval, (key, value) -> key);
		return ServerResponse
				.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(zipped, Rates.class);
	}

}
