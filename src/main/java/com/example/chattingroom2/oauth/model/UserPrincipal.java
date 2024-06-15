package com.example.chattingroom2.oauth.model;

import com.example.chattingroom2.type.Provider;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Builder
public class UserPrincipal implements OAuth2User, UserDetails {

    private Long id;

    private String socialId;

    private String name;

    private String email;

    private Provider provider;

    public UserPrincipal(Long id, String socialId, String email, String name, Provider provider) {
        this.id = id;
        this.socialId = socialId;
        this.email = email;
        this.name = name;
        this.provider = provider;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return this.socialId;
    }
}
