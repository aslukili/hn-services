package org.loukili.hngateway.feign;


import org.loukili.hngateway.dto.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hn-user")
public interface UserClient {
    @GetMapping("/api/v1/hn-user/auth/validate-token")
    ResponseDto validateToken(@RequestParam("token") String token);
}
