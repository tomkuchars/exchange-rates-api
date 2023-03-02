package com.app.exchangerates.reactive;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class RemoveCharsetWebFilter implements WebFilter { // TODO: removing charset from
																														// text/event-stream;charset=UTF-8 does not work here

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// exchange.getResponse()
		// .getHeaders().clearContentHeaders();
		// exchange.getResponse()
		// .getHeaders().setContentType(MediaType.TEXT_EVENT_STREAM);
		return chain.filter(exchange);
	}
}