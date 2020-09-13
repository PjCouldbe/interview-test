package com.optisystems.interview.test.model;

import com.optisystems.interview.test.model.base.AbstractValidityPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(
    name = "group_validity_period",
    indexes = {
        @Index(columnList = "id", name = "group_validity_period_id_idx")
    }
)
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupValidityPeriod extends AbstractValidityPeriod {

    @Audited(targetAuditMode = NOT_AUDITED)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PermissionGroup group;

}
