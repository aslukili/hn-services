package org.loukili.hnpost.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@FeignClient(name = "hn-user")
public interface UserClient {
    @PutMapping("/api/v1/hn-user/users/{username}/karma")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> incrementKarma(@PathVariable String username, @RequestHeader Map<String, String> headers);
}
