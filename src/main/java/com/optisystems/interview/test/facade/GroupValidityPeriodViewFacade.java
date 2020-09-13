package com.optisystems.interview.test.facade;

import com.optisystems.interview.test.model.GroupValidityPeriodView;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public interface GroupValidityPeriodViewFacade {

    GroupValidityPeriodView findByUserId(UUID userId);

    GroupValidityPeriodView findByUserId(UUID userId, DateTime targetTime);

    List<GroupValidityPeriodView> findByUserId(UUID userId, DateTime from, DateTime to);
    
}
