package com.optisystems.interview.test.model.base;

import com.optisystems.interview.test.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import javax.persistence.*;
import java.util.UUID;


/**
 * Сущность со сроком действия и с привязкой к {@link User}
 */
@MappedSuperclass
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractValidityPeriod extends AbstractBaseEntity implements ValidityPeriod, Cloneable {

    private static final long serialVersionUID = 1L;

    /**
     * Связанный пользователь
     */
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    /**
     * Дата начала действия.
     */
    @Basic(optional = false)
    @Column(name = "start_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
        parameters = {@org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
            @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")})
    private DateTime startDate;

    /**
     * Дата окончания действия.
     */
    @Basic(optional = false)
    @Column(name = "end_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
        parameters = {@org.hibernate.annotations.Parameter(name = "databaseZone", value = "UTC"),
            @org.hibernate.annotations.Parameter(name = "javaZone", value = "UTC")})
    private DateTime endDate;

    @Override
    public Interval getInterval() {
        return new Interval(startDate, endDate);
    }

    @Override
    public UUID getUserId() {
        return user.getId();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
