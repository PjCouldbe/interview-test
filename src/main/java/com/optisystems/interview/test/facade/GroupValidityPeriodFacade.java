package com.optisystems.interview.test.facade;

import com.optisystems.interview.test.facade.base.AbstractValidityPeriodFacade;
import com.optisystems.interview.test.model.GroupValidityPeriod;
import com.optisystems.interview.test.model.PermissionGroup;
import com.optisystems.interview.test.model.User;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("JpaQlInspection")
@Service
public class GroupValidityPeriodFacade extends AbstractValidityPeriodFacade<GroupValidityPeriod> {

    private final PermissionGroupFacade permissionGroupFacade;

    public GroupValidityPeriodFacade(
        PermissionGroupFacade permissionGroupFacade
    ) {
        super(GroupValidityPeriod.class);

        this.permissionGroupFacade = permissionGroupFacade;
    }

    public List<UUID> findAllAvailableHiredEmployeeIdsByGroup(PermissionGroup group, DateTime dateTime) {
        return findAllAvailableHiredEmployeeIdsByGroups(
            permissionGroupFacade.findAllAvailable(group),
            dateTime
        );
    }

    public List<UUID> findAllAvailableHiredEmployeeIdsByGroups(Collection<PermissionGroup> groups, DateTime dateTime) {
        return entityManager.createQuery(
            "select u.id " +
                "from GroupValidityPeriod gvp " +
                "join User u on gvp.user = u " +
                "join PermissionGroup g on gvp.group = g " +
                "where gvp.group in (:groups) and " +
                "u.isEmployee = true and " +
                "u.firingDate > :targetTime and " +
                "u.hiringDate < :targetTime and " +
                "gvp.startDate <= :targetTime and " +
                "gvp.endDate > :targetTime", UUID.class)
            .setParameter("groups", groups)
            .setParameter("targetTime", dateTime)
            .getResultList();
    }

    public List<UUID> findAllEmployeeIdsByGroups(Collection<PermissionGroup> groups) {
        return findAllEmployeeIdsByGroups(
            groups, DateTime.now(DateTimeZone.UTC)
        );
    }

    public List<UUID> findAllEmployeeIdsByGroups(Collection<PermissionGroup> groups, DateTime dateTime) {
        return entityManager.createQuery(
            "select u.id " +
                "from GroupValidityPeriod gvp " +
                "join User u on gvp.user = u " +
                "join PermissionGroup g on gvp.group = g " +
                "where gvp.group in (:groups) and " +
                "u.isEmployee = true and " +
                "gvp.startDate <= :targetTime and gvp.endDate > :targetTime", UUID.class)
            .setParameter("groups", groups)
            .setParameter("targetTime", dateTime)
            .getResultList();
    }

    public List<UUID> findAllUserIdsByGroups(Collection<UUID> groupIds) {
        return entityManager.createQuery(
            "select u.id " +
                "from GroupValidityPeriod gvp " +
                "join User u on gvp.user = u " +
                "join PermissionGroup g on gvp.group = g " +
                "where gvp.group.id in (:groupIds) and " +
                "gvp.startDate <= :targetTime and gvp.endDate > :targetTime", UUID.class)
            .setParameter("groupIds", groupIds)
            .setParameter("targetTime", DateTime.now(DateTimeZone.UTC))
            .getResultList();
    }

    public List<User> findAllUserByGroups(Collection<UUID> groupIds) {
        return entityManager.createQuery(
            "select distinct u " +
                "from GroupValidityPeriod gvp " +
                "join User u on gvp.user = u " +
                "join PermissionGroup g on gvp.group = g " +
                "where gvp.group.id in (:groupIds) and " +
                "gvp.startDate <= :targetTime and gvp.endDate > :targetTime", User.class)
            .setParameter("groupIds", groupIds)
            .setParameter("targetTime", DateTime.now(DateTimeZone.UTC))
            .getResultList();
    }
}
