package com.optisystems.interview.test.model.base;

import com.optisystems.interview.test.model.PermissionGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractMultiGroupedEntity extends AbstractBaseEntity {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        inverseJoinColumns = @JoinColumn(
            name = "group_id",
            nullable = false
        )
    )
    protected List<PermissionGroup> groups = new ArrayList<>();
}
