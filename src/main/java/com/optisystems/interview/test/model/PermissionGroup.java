package com.optisystems.interview.test.model;

import com.optisystems.interview.test.model.base.AbstractBaseEntity;
import com.optisystems.interview.test.model.base.GroupedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Группа допусков орг-структуры
 *
 * @author Tkach
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    name = "permission_group",
    indexes = {
        @Index(columnList = "id", name = "permission_group_id_idx"),
        @Index(columnList = "external_id", name = " external_permission_group_id_idx")
    }
)
@Getter
@Setter
public class PermissionGroup extends AbstractBaseEntity implements GroupedEntity {

    /**
     * Имя группы
     */
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    /**
     * Родительская группа, nullable
     */
    @ManyToOne
    private PermissionGroup parent;

    /**
     * Id внешней системы
     */
    @Basic(optional = false)
    @Column(name = "external_id", unique = true)
    private String externalId;

    /**
     * список привязанных орг структук
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "permission_group_mapping",
        joinColumns = {
            @JoinColumn(name = "from_group_id", referencedColumnName = "id", nullable = false, updatable = false)
        },
        inverseJoinColumns = {
            @JoinColumn(name = "to_group_id", referencedColumnName = "id", nullable = false, updatable = false)
        }
    )
    private List<PermissionGroup> relatedGroups;

    public PermissionGroup(UUID id, String name, PermissionGroup parent, String externalId) {
        setPK(id);
        this.name = name;
        this.parent = parent;
        this.externalId = externalId;
    }

    public PermissionGroup(UUID id, String name, PermissionGroup parent) {
        this(id, name, parent, null);
    }

    public PermissionGroup(String name, PermissionGroup parent) {
        this.name = name;
        this.parent = parent;
    }

    public PermissionGroup(String name, String externalId, PermissionGroup parent) {
        this.name = name;
        this.externalId = externalId;
        this.parent = parent;
    }

    public PermissionGroup(String name) {
        this(name, null);
    }

    public PermissionGroup() {
    }

    @Override
    public PermissionGroup getGroup() {
        return parent;
    }

    @Override
    public void setGroup(PermissionGroup parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        PermissionGroup other = (PermissionGroup) obj;

        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }

        UUID parent = this.parent != null ? this.parent.getPK() : null;
        UUID otherParent = other.parent != null ? other.parent.getPK() : null;

        if (!Objects.equals(parent, otherParent)) {
            return false;
        }

        return Objects.equals(this.getPK(), other.getPK());
    }

    @Override
    public String toString() {
        return "PermissionGroup{" +
            "name='" + name + '\'' +
            ", id=" + id +
            ", parent=" + parent +
            ", externalId=" + externalId +
            '}';
    }
}
