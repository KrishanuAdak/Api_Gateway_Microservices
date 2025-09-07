package com.example.demo1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private User_Role_Based_Key_Resolver keyResolver;

    // Centralized RedisRateLimiter beans

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(15, 25); // 3 requests/sec, burst 5
    }

//    @Autowired
//    @Qualifier("patientRateLimiter")
//    public RedisRateLimiter patientRateLimiter() {
//        return new RedisRateLimiter(15, 25); // 15 requests/sec, burst 25
//    }
//
//    @Autowired
//    @Qualifier("appointmentRedisLimiter")
//    public RedisRateLimiter appointmentRateLimiter() {
//        return new RedisRateLimiter(25, 35); // 25 requests/sec, burst 35
//    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

            // Doctor Service
            .route("doctor-service", r -> r.path("/doctor/**")
                .filters(f -> f.filter(jwtAuthenticationFilter)
                    .requestRateLimiter(c -> {
                        c.setKeyResolver(keyResolver);
                        c.setRateLimiter(redisRateLimiter());
                    })
                )
                .uri("lb://doctor-service")
            )

            // Patient Service
            .route("patients-service", r -> r.path("/patients/**")
                .filters(f -> f.filter(jwtAuthenticationFilter)
                    .requestRateLimiter(c -> {
                        c.setKeyResolver(keyResolver);
                        c.setRateLimiter(redisRateLimiter());
                    })
                )
                .uri("lb://patients-service")
            )

            // Admin Service (no rate limiting)
            .route("admin-service", r -> r.path("/admin/**")
                .filters(f -> f.filter(jwtAuthenticationFilter))
                .uri("lb://admin-service")
            )

            // Auth Service (public)
            .route("auth-service", r -> r.path("/auth-service/**")
                .uri("lb://auth-service")
            )

            // Appointment Service
            .route("appointment-service", r -> r.path("/appointment/**")
                .filters(f -> f.filter(jwtAuthenticationFilter)
                    .requestRateLimiter(c -> {
                        c.setKeyResolver(keyResolver);
                        c.setRateLimiter(redisRateLimiter());
                    })
                )
                .uri("lb://appointment-service")
            )

            // Notification Service (public or no JWT)
            .route("notification-service", r -> r.path("/notification/**")
                .uri("lb://notification-service")
            )

            .build();
    }
}
