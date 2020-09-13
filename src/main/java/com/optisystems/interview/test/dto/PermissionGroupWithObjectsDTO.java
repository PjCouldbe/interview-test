package com.optisystems.interview.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionGroupWithObjectsDTO extends PermissionGroupDTO implements Serializable {

    private List<Object> children = new ArrayList<>();

    public PermissionGroupWithObjectsDTO(UUID id, String name, List<Object> children, String externalId) {
        super.setId(id);
        super.setGroupName(name);
        super.setExternalGroupId(externalId);

        this.children = children;
    }

    public PermissionGroupWithObjectsDTO(UUID id, UUID groupId, String name, List<Object> children, String externalId) {
        super.setId(id);
        super.setGroupId(groupId);
        super.setGroupName(name);
        super.setExternalGroupId(externalId);

        this.children = children;
    }

    @JsonIgnore()
    private boolean withObjects;

    @Override
    @JsonProperty("isGroup")
    public Boolean isGroup() {
        return true;
    }
}
