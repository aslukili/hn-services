package org.loukili.hngateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.loukili.hngateway.dto.HnUserDto;
import org.loukili.hngateway.dto.ResponseDto;
import org.loukili.hngateway.feign.UserClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;


    public AuthFilter(@Lazy UserClient userFeignClient, WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException(" no key authorization");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length !=2 || !"Bearer".equals(parts[0])){
                throw new RuntimeException("Authorization doesn't have a Bearer token");
            }

            return webClientBuilder.build()
                    .get()
                    .uri("http://hn-user/api/v1/hn-user/auth/validate-token?token=" + parts[1])
                    .retrieve().bodyToMono(ResponseDto.class)
                    .map(response -> {
                        if (response.getStatus().equals("bad request")){
                            new ResponseDto("bad request", response.getMessage());
                            throw new RuntimeException(response.getMessage());
                        }
                        HnUserDto hnUserDto = objectMapper.convertValue(response.getData(), HnUserDto.class);
                        exchange.getRequest()
                                .mutate()
                                .header("X-auth-user-id", String.valueOf(hnUserDto.getId()));
                        return exchange;
                    }).flatMap(chain::filter);
        };
    }

    public static class Config{

    }
}
