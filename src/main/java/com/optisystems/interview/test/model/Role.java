package com.optisystems.interview.test.model;

import com.optisystems.interview.test.model.base.AbstractBaseEntity;
import com.optisystems.interview.test.model.base.RoleEnum;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Таблица типов юзеров.
 * Таблица с константами, т.е должна быть наполнена при инсталяции.
 *
 * @author bigblackbug
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
@Table(name = "role")
@Audited
public class Role extends AbstractBaseEntity {

    /**
     * Название типа.
     */
    @Basic(optional = false)
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(mappedBy = "role")
    private List<User> userList = new ArrayList<>();

    protected Role() {
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

        Role other = (Role) obj;

        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }

        return Objects.equals(this.getPK(), other.getPK());
    }

}
