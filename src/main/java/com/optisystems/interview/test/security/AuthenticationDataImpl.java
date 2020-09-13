package com.optisystems.interview.test.security;

import com.optisystems.interview.test.facade.UserFacade;
import com.optisystems.interview.test.model.userdetails.UserDetailsNewImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component("AuthenticationDataImpl")
@RequiredArgsConstructor
public class AuthenticationDataImpl implements AuthenticationData {

    private final UserFacade userFacade;

    @Override
    public UserDetailsNewImpl getUserDetails() {
        return (UserDetailsNewImpl) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

}
