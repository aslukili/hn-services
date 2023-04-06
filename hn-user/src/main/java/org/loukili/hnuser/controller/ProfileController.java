package org.loukili.hnuser.controller;


import org.loukili.hnuser.dto.HnUserResponse;
import org.loukili.hnuser.entity.HnUser;
import org.loukili.hnuser.exception.UserNotFoundException;
import org.loukili.hnuser.service.HnUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/hn-user")
public class ProfileController {
    private final HnUserService userService;

    @Autowired
    public ProfileController(HnUserService userService) {
        this.userService = userService;
    }
    @GetMapping("/profile")
    public ResponseEntity<HnUserResponse> profile(@AuthenticationPrincipal HnUser user) {
        String userUsername = user.getUsername(); // assuming email is extracted from the JWT token
        Optional<HnUser> optionalUser = this.userService.findByUsername(userUsername);

        HnUser foundUser = optionalUser.orElseThrow(() -> new UserNotFoundException(userUsername));

//        if (!foundUser.getEmail().equals(userEmail)) {
//            throw new UnauthorizedUserException("Unauthorized user");
//        }

        return ResponseEntity.ok(foundUser.toResponse());
    }
}
