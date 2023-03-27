package org.loukili.hnuser.auth;

import lombok.RequiredArgsConstructor;
import org.loukili.hnuser.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@RestController
@RequestMapping("/api/v1/hn-user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        System.out.println("here");
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    // TODO: add a logout endpoint
    @GetMapping("/validate-token")
    public ResponseDto validateToken(@PathParam("token") String token){
        return authenticationService.validateToken(token);
    }
}
