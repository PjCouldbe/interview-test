package com.optisystems.interview.test.model;

import com.optisystems.interview.test.model.base.AbstractMultiGroupedEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    name = "skill",
    indexes = {
        @Index(columnList = "id", name = "skill_id_idx")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "externalId"})
public class Skill extends AbstractMultiGroupedEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name) &&
                Objects.equals(externalId, skill.externalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, externalId);
    }
}
