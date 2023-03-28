package org.loukili.hnuser.dto;


import lombok.Builder;
import lombok.Data;
import org.loukili.hnuser.entity.HnUser;
import org.loukili.hnuser.entity.Role;

@Data
@Builder
public class HnUserRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String about;
    private Role role;
    public HnUser toUser() {
        return HnUser.builder()
                .username(username)
                .password(password)
                .email(email)
                .fullName(fullName)
                .about(about)
                .role(role)
                .build();
    }
}
