package com.optisystems.interview.test.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.UUID;

public interface ValidityPeriod {

    UUID getId();

    UUID getUserId();

    DateTime getStartDate();

    DateTime getEndDate();

    @JsonIgnore
    default Interval getInterval() {
        return new Interval(getStartDate(), getEndDate());
    }
}