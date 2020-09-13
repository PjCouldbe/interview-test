package com.optisystems.interview.test.model;

import com.optisystems.interview.test.model.base.ValidityPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class GroupValidityPeriodView implements ValidityPeriod, Serializable {

    private UUID id;
    /**
     * Точка оргструктуры в котором юзер работает.
     */
    private UUID groupId;
    private UUID parentGroupId;
    private String externalGroupId;
    @Column(name = "user_id", insertable = false, updatable = false)
    private UUID userId;
    private String name;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
        parameters = {@org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
            @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")})
    private DateTime startDate;

    /**
     * Дата окончания действия.
     */
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
        parameters = {@org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
            @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")})
    private DateTime endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupValidityPeriodView that = (GroupValidityPeriodView) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(groupId, that.groupId) &&
            Objects.equals(parentGroupId, that.parentGroupId) &&
            Objects.equals(externalGroupId, that.externalGroupId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, parentGroupId, externalGroupId, userId, name, startDate, endDate);
    }
}