package com.app.exchangerates.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.beans.factory.annotation.Value;

@Configuration(proxyBeanMethods = false)
public class ExchangeRouter {

	@Value("classpath:pages/rates.html")
	private Resource ratesHtml;

	@Bean
	public RouterFunction<ServerResponse> route(ExchangeHandler exchangeHandler) {

		return RouterFunctions
				.route(GET("/rates"), request -> pageResponse(ratesHtml))
				.andRoute(GET("/exchange/rates"), exchangeHandler::events);
	}

	private static Mono<ServerResponse> pageResponse(Resource page) {
		return ServerResponse
				.ok()
				.contentType(MediaType.TEXT_HTML)
				.body(DataBufferUtils.read(page, new DefaultDataBufferFactory(), 4096), DataBuffer.class);
	}
}
