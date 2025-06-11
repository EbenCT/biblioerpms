package com.sw2parcial2.biblioerpms.dto.response;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, Long id, String name, String email,
                       Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }
}
