package com.optisystems.interview.test.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optisystems.interview.test.model.PermissionGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO extends AbstractIdDTO implements Serializable {

    private String externalGroupId;
    private String groupName;

    public GroupDTO(UUID id) {
        super(id);
    }

    public GroupDTO(UUID id, String externalGroupId, String groupName) {
        super(id);

        this.externalGroupId = externalGroupId;
        this.groupName = groupName;
    }

    @JsonIgnore
    private PermissionGroup group;
}
