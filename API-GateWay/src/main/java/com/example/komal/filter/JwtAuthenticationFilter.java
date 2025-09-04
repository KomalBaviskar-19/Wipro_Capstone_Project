package com.example.komal.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.komal.utils.JwtUtil;

import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Skip authentication for auth endpoints
            if (request.getURI().getPath().contains("/api/auth/")) {
                return chain.filter(exchange);
            }

            // Check Authorization header
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            // Validate token
            if (!jwtUtil.validateToken(token)) {
                return onError(exchange, "Invalid or expired JWT token", HttpStatus.UNAUTHORIZED);
            }

            String userRole = jwtUtil.extractRole(token);
            if (userRole == null) {
                return onError(exchange, "No role found in token", HttpStatus.FORBIDDEN);
            }

            // Role-based access check
            if (config.isRequiresRole() && !isAuthorized(userRole, config.getRequiredRoles())) {
                return onError(exchange, "Insufficient permissions", HttpStatus.FORBIDDEN);
            }

            // Add user info to headers for downstream services
            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-Auth-User-Email", jwtUtil.extractUsername(token))
                    .header("X-Auth-User-Role", userRole)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    private boolean isAuthorized(String userRole, List<String> requiredRoles) {
        if (userRole.equals("ADMIN")) return true; // Admin can access everything
        if (requiredRoles == null || requiredRoles.isEmpty()) return true; // No specific roles required
        return requiredRoles.contains(userRole); // Check if user role exists in allowed roles
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    // Config class for multi-role support
    public static class Config {
        private boolean requiresRole = false;
        private List<String> requiredRoles;

        public boolean isRequiresRole() {
            return requiresRole;
        }

        public void setRequiresRole(boolean requiresRole) {
            this.requiresRole = requiresRole;
        }

        public List<String> getRequiredRoles() {
            return requiredRoles;
        }

        public void setRequiredRoles(List<String> requiredRoles) {
            this.requiredRoles = requiredRoles;
            this.requiresRole = true;
        }
    }
}
