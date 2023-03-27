package org.loukili.hnuser.auth;

import lombok.RequiredArgsConstructor;
import org.loukili.hnuser.config.JwtService;
import org.loukili.hnuser.dto.ResponseDto;
import org.loukili.hnuser.entity.HnUser;
import org.loukili.hnuser.entity.Role;
import org.loukili.hnuser.repository.HnUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final HnUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // TODO: validate request (email and username are unique!)
        HnUser user = HnUser.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .email(request.getEmail())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        HnUser user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public ResponseDto validateToken(String token) {
        final String userUsername = jwtService.extractUsername(token);
        Optional<HnUser> user =  userRepository.findByUsername(userUsername);

        if (user.isEmpty()){
            return new ResponseDto("bad request","user not found");
        }
        else if (userUsername.equals(user.get().getUsername()) && !jwtService.isTokenExpired(token)) {
            jwtService.generateToken(user.get());
            return new ResponseDto("success","user user", user);
        }else{
            return new ResponseDto("success","no user email of token or token expired");
        }
    }
}
