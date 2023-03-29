package org.loukili.hnuser.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnuser.entity.HnUser;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HnUserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private int karma;
    private String role;
    private String about;
    private boolean isEmailNotPublic;
    private List<HnUser> followers;
    private List<HnUser> following;
}
