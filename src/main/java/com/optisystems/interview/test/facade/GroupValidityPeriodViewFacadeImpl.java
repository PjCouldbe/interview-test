package com.optisystems.interview.test.facade;

import com.google.common.collect.Lists;
import com.optisystems.interview.test.model.GroupValidityPeriodView;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("JpaQlInspection")
@Service
public class GroupValidityPeriodViewFacadeImpl implements GroupValidityPeriodViewFacade {

    private final EntityManager entityManager;

    public GroupValidityPeriodViewFacadeImpl(
        EntityManager entityManager
    ) {
        this.entityManager = entityManager;
    }

    @Override
    public GroupValidityPeriodView findByUserId(UUID userId) {
        return findByUserId(userId, DateTime.now(DateTimeZone.UTC));
    }

    @Override
    public GroupValidityPeriodView findByUserId(UUID userId, DateTime targetTime) {
        return findByUserId(userId, targetTime, targetTime)
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format(
                        "GroupValidityPeriod is not found by User: %s and interval: %s", userId, targetTime
                )));
    }

    @Override
    public List<GroupValidityPeriodView> findByUserId(UUID userId, DateTime from, DateTime to) {
        return entityManager.createQuery(
            "select distinct new com.optisystems.wfmmodel.basic.permissions.view.GroupValidityPeriodView(gvp.id, gvp.group.id, g.parent.id, u.id, g.externalId, g.name, gvp.startDate, gvp.endDate) " +
                        "from GroupValidityPeriod gvp " +
                        "join User u on gvp.user = u " +
                        "join PermissionGroup g on gvp.group = g " +
                        "where u.id in (:ids)" +
                        " and ((:fromDate = :toDate\n" +
                        " and gvp.startDate <= :fromDate\n" +
                        " and gvp.endDate > :fromDate)\n" +
                        "  or (gvp.startDate < :toDate\n" +
                        " and gvp.endDate > :fromDate))"
                , GroupValidityPeriodView.class
        )
                .setParameter("fromDate", from)
                .setParameter("toDate", to)
                .setParameter("ids", Lists.newArrayList(userId))
                .getResultList();
    }

}
