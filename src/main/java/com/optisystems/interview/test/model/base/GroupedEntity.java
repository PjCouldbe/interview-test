package com.optisystems.interview.test.model.base;

import com.optisystems.interview.test.model.PermissionGroup;

public interface GroupedEntity {

    PermissionGroup getGroup();

    void setGroup(PermissionGroup group);

}
