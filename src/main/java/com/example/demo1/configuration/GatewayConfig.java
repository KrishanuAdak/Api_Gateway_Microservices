package com.example.demo1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	    @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;

	    @Bean
	    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
	        return builder.routes()

	                // Doctor Service
	                .route("doctor-service", r -> r.path("/doctor/**")
	                        .filters(f -> f.filter(jwtAuthenticationFilter))
	                        .uri("lb://doctor-service"))

	                // Patient Service
	                .route("patients-service", r -> r.path("/patients/**")
	                        .filters(f -> f.filter(jwtAuthenticationFilter))
	                        .uri("lb://patients-service"))

	                // Admin Service
	                .route("admin-service", r -> r.path("/admin/**")
	                        .filters(f -> f.filter(jwtAuthenticationFilter))
	                        .uri("lb://admin-service"))
	                
	                .route("auth-service",r->r.path("auth-service/**")	                		
	                		.uri("lb://auth-service"))
	                
                     .route("appointment-service",r->r.path("/appointment/**")
                    		 .filters(f -> f.filter(jwtAuthenticationFilter))
	                        .uri("lb://appointment-service"))
	                
	                

	                .build();
	    }
	    
}



