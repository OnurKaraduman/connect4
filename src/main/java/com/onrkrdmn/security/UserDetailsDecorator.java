package com.onrkrdmn.security;

import com.onrkrdmn.domain.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by onur on 28.01.17.
 * decorator class implemented {@link UserDetails} for oauth2 configuration
 */
public class UserDetailsDecorator implements UserDetails {

    public static final String ROLES_PREFIX = "ROLE_";

    private Player player;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDetailsDecorator(Player player) {
        this.player = player;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles[] = player.getRoles();

        if (roles == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(roles).map(
                role -> (GrantedAuthority) () -> ROLES_PREFIX + role
        ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return player.getGameToken();
    }

    @Override
    public String getUsername() {
        return player.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
