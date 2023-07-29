package com.example.apigatewayservice.filter;

import com.example.apigatewayservice.jwt.JwtTokenDto;
import com.example.apigatewayservice.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final Environment env;
    private final RedisService redisService;

    public AuthorizationHeaderFilter(Environment env, RedisService redisService) {
        super(Config.class);
        this.env = env;
        this.redisService = redisService;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Authorization Header가 빈 값 입니다.", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");
            String tokenSecret = env.getProperty("token.secret");

            if (!isJwtValid(jwt, tokenSecret)) {
                return onError(exchange, "정상적인 토큰이 아닙니다", HttpStatus.UNAUTHORIZED);
            }

            Claims tokenClaims = getTokenClaims(jwt, tokenSecret);
            String userId = String.valueOf(tokenClaims.get("userId"));
            log.info("userId = {}", userId);

            JwtTokenDto tokenDto = redisService.getValues(userId, JwtTokenDto.class).orElse(new JwtTokenDto());
            if (tokenDto == null) {
                return onError(exchange, "해당 아이디로 발급된 토큰이 없습니다", HttpStatus.UNAUTHORIZED);
            }

            log.info("tokenDto = {}", tokenDto);

            if (!tokenDto.getAccessToken().equals(jwt.trim())) {
                return onError(exchange, "해당 사용자의 Access 토큰이 정확하지 않습니다", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    // mono, flux -> Spring Webflux
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwt, String tokenSecret) {
        boolean returnVal = true;
        String subject = null;

        try {
            subject = getTokenClaims(jwt, tokenSecret)
                    .getSubject();
        } catch (Exception e) {
            log.error(e.getMessage());
            returnVal = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnVal = false;
        }

        return returnVal;
    }

    // payload 에 있는 Claims 정보 가져오는 메서드
    public Claims getTokenClaims(String token, String tokenSecret) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
        return claims;
    }
}
