package com.optisystems.interview.test.facade;

import com.optisystems.interview.test.facade.base.AbstractReadOnlyFacade;
import com.optisystems.interview.test.model.Role;
import com.optisystems.interview.test.model.base.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by bbr on 03.07.16.
 */
@Service
public class RoleFacade extends AbstractReadOnlyFacade<Role> {

    private final HashMap<RoleEnum, Role> cache = new HashMap<>();

    public RoleFacade() {
        super(Role.class);
    }
    
    @Override
    public List<Role> findAll() {
        List<Role> roles = super.findAll();
        if (cache.isEmpty()) {
            for (Role role : roles) {
                cache.put(role.getName(), role);
            }
        }
        return roles;
    }

}
