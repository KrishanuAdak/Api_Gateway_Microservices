package com.example.demo1.configuration;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class User_Role_Based_Key_Resolver implements KeyResolver{

	@Override
	public Mono<String> resolve(ServerWebExchange exchange) {
        String user_id=exchange.getRequest().getHeaders().getFirst("X-User-Id");
        String user_role=exchange.getRequest().getHeaders().getFirst("X-Role");
        if(user_role!=null && user_id!=null) {
        	return Mono.just(user_role+" "+user_id);
        	
        }
		return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostName());
	}

}
