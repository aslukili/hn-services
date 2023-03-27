package org.loukili.hnuser.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.loukili.hnuser.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HnUserDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private int karma;
    private String role;
    private String about;
    private boolean isEmailNotPublic;
}
