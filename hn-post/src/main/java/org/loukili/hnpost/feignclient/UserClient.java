package org.loukili.hnpost.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "hn-user")
public interface UserClient {
    @PutMapping("/{username}/karma")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> incrementKarma(@PathVariable String username);


}
