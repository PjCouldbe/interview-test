package com.optisystems.interview.test.model.userdetails;

import com.optisystems.interview.test.model.User;
import com.optisystems.interview.test.model.base.RoleEnum;
import lombok.Getter;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class UserDetailsInfo implements Serializable {
    private static final long serialVersionUID = 49196449519369339L;

    private final UUID userId;

    private final String login;
    private final String hash;

    private final DateTime hiringDate;
    private final DateTime firingDate;

    private final RoleEnum role;

    private final int authAttempts;
    private final boolean isUserBlocked;

    public UserDetailsInfo(User user) {
        userId = user.getId();

        login = user.getLogin();
        hash = user.getHash();

        hiringDate = user.getHiringDate();
        firingDate = user.getFiringDate();

        role = user.getRole().getName();

        authAttempts = user.getAttempts();
        isUserBlocked = user.isBlocked();
    }
}
