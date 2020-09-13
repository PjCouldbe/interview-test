package com.optisystems.interview.test.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optisystems.interview.test.dto.base.GroupIdDTO;
import com.optisystems.interview.test.model.PermissionGroup;
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
public class PermissionGroupDTO extends GroupIdDTO<PermissionGroup> implements Serializable {

    /**
     * группы куда можно выйти на подработку.
     */
    private List<PermissionGroupDTO> relatedGroups = new ArrayList<>();

    @JsonIgnore
    private List<PermissionGroup> relatedGroupsEntities = new ArrayList<>();

    public PermissionGroupDTO(UUID id, String groupName, UUID groupId, String externalGroupId) {
        setId(id);
        setGroupId(groupId);
        setExternalGroupId(externalGroupId);
        setGroupName(groupName);
    }

    public boolean isRoot() {
        return getGroupId() == null;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return getGroupName();
    }
}
