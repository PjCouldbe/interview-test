package com.optisystems.interview.test.dto.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.optisystems.interview.test.model.PermissionGroup;
import com.optisystems.interview.test.model.base.AbstractBaseEntity;
import com.optisystems.interview.test.model.base.GroupedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class GroupIdDTO<Entity extends AbstractBaseEntity & GroupedEntity>
    extends AbstractIdDTO<Entity> implements Naming {

    private UUID groupId;
    private String groupName;
    private String externalGroupId;

    @JsonIgnore
    private PermissionGroup group;

    @JsonProperty("isGroup")
    public Boolean isGroup() {
        return false;
    }

    @JsonProperty("isGroup")
    public void setIsGroup(boolean isGroup) {

    }
}
