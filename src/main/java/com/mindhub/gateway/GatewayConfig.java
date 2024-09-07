package com.mindhub.gateway;

import com.mindhub.gateway.filter.JwtAuthenticationFilter;
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
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user_route", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://userservice"))
                .route("auth_route", r -> r.path("/api/auth/**")
                        .uri("lb://userservice"))
                .route("task_route", r -> r.path("/api/tasks/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://taskservice"))
                .build();
    }
}