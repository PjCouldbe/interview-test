package com.optisystems.interview.test.model.userdetails;

import com.optisystems.interview.test.model.User;
import lombok.Getter;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Здесь нужно реализовывать логику проверки пользователя при авторизации.
 */
@Getter
public class UserDetailsNewImpl implements UserDetails {
    private static final long serialVersionUID = -5667862230776632584L;
    private final UserDetailsInfo user;

    private final int numberOfAuthorizationAttempts;

    public UserDetailsNewImpl(User user, int numberOfAuthorizationAttempts) {
        this.user = new UserDetailsInfo(user);
        this.numberOfAuthorizationAttempts = numberOfAuthorizationAttempts;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return authorities;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (user.isUserBlocked()) return false;
    
        return numberOfAuthorizationAttempts == 0 || user.getAuthAttempts() < numberOfAuthorizationAttempts;
    }

    @Override
    public String getPassword() {
        return user.getHash();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getFiringDate().plusDays(1).isAfter(DateTime.now());
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    //using in SpEL
    @SuppressWarnings("unused")
    public String roleToString() {
        return user.getRole().name();
    }
}