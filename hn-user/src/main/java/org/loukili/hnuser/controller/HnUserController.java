package org.loukili.hnuser.controller;


import lombok.RequiredArgsConstructor;
import org.loukili.hnuser.dto.HnUserRequest;
import org.loukili.hnuser.dto.HnUserResponse;
import org.loukili.hnuser.entity.HnUser;
import org.loukili.hnuser.exception.UserNotFoundException;
import org.loukili.hnuser.service.HnUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hn-user/users")
@RequiredArgsConstructor
public class HnUserController {
    private final HnUserService hnUserService;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<HnUserResponse> getAllUsers(){
        return hnUserService.getAll();
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<HnUserResponse> getAllUsersByPage(@RequestParam int page, @RequestParam int size){
        return hnUserService.getAllByPage(page, size);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public HnUserResponse saveUser(@RequestBody HnUserRequest userRequest){
        return hnUserService.saveUser(userRequest);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public HnUserResponse findUserByUsername(@PathVariable String username){
        HnUser user = hnUserService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return user.toResponse();
    }

//    @GetMapping("/{userUsername}")
//    @ResponseStatus(HttpStatus.OK)
//    public HnUserResponse findUserByUsername(@PathVariable String userUsername){
//        HnUser user = hnUserService.findByUsername(userUsername)
//                .orElseThrow(() -> new UserNotFoundException(userUsername));
//
//        return user.toResponse();
//    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public HnUserResponse editSubmission(@PathVariable Long userId, @RequestBody HnUserRequest userRequest){
        return hnUserService.editUser(userId, userRequest);
    }

    @PutMapping("/{username}/karma")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<?> incrementKarma(@PathVariable String username){
        if (hnUserService.incrementKarma(username)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteSubmission(@PathVariable Long userId) {
        return hnUserService.deleteUser(userId);
    }


    @PostMapping("/{userId}/follow")
    // TODO: the response entity should not be like this!
    public ResponseEntity<?> followUser(@PathVariable Long userId, Authentication authentication){
        Long followerId = ((HnUser) authentication.getPrincipal()).getId();
        if (hnUserService.followUser(followerId, userId)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{userId}/unfollow")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId, Authentication authentication){
        Long followerId = ((HnUser) authentication.getPrincipal()).getId();
        if (hnUserService.unfollowUser(followerId, userId)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
