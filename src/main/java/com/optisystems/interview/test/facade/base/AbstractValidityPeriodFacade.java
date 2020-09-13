package com.optisystems.interview.test.facade.base;

import com.optisystems.interview.test.model.base.AbstractValidityPeriod;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public abstract class AbstractValidityPeriodFacade<T extends AbstractValidityPeriod> extends AbstractFacade<T> {

    public AbstractValidityPeriodFacade(Class<T> entityClass) {
        super(entityClass);
    }

    @Transactional(readOnly = true)
    public List<T> findByUserIds(Collection<UUID> userIds) {
        return findByUserIdsAndDateTime(userIds, DateTime.now(DateTimeZone.UTC));
    }

    @Transactional(readOnly = true)
    public List<T> findByUserIdsAndDateTime(Collection<UUID> userIds, DateTime eventTime) {
        return userIds.isEmpty() ?
            new ArrayList<>() :
            retrieve(
                entityClass,
                (builder, criteria, root) -> criteria.select(root)
                    .where(root.get("user").get("id").in(userIds),
                        builder.greaterThan(root.get("endDate"), eventTime),
                        builder.lessThanOrEqualTo(root.get("startDate"), eventTime))
            );
    }

    @Transactional(readOnly = true)
    public T findByUserId(UUID userId) {
        return findByUserIdAndDateTime(userId, DateTime.now(DateTimeZone.UTC));
    }

    @Transactional(readOnly = true)
    public T findByUserIdAndDateTime(UUID userId, DateTime eventTime) {
        return retrieveOneOrNull(
            entityClass,
            (builder, criteria, root) -> criteria.select(root)
                .where(builder.equal(root.get("user").get("id"), userId),
                    builder.greaterThan(root.get("endDate"), eventTime),
                    builder.lessThanOrEqualTo(root.get("startDate"), eventTime))
        );
    }

    @Transactional(readOnly = true)
    public List<T> findByPeriod(DateTime from, DateTime to) {
        return retrieve(
            entityClass,
            (builder, criteria, root) -> criteria.select(root)
                .where(builder.greaterThan(root.get("endDate"), from),
                    builder.lessThan(root.get("startDate"), to))
        );
    }

    @Transactional(readOnly = true)
    public List<T> findByUserIdAndPeriod(UUID userId, DateTime from, DateTime to) {
        return retrieve(
            entityClass,
            (builder, criteria, root) -> criteria.select(root)
                .where(builder.equal(root.get("user").get("id"), userId),
                    builder.greaterThan(root.get("endDate"), from),
                    builder.lessThan(root.get("startDate"), to))
        );
    }

    @Transactional(readOnly = true)
    public List<T> findAllByUserIdWithinPeriod(UUID userId, DateTime from, DateTime to) {
        return retrieve(
            entityClass,
            (builder, criteria, root) -> criteria.select(root)
                .where(
                    builder.equal(root.get("user").get("id"), userId),
                    builder.lessThanOrEqualTo(root.get("startDate"), from),
                    builder.greaterThan(root.get("endDate"), to)
                )
        );
    }

    @Transactional(readOnly = true)
    public List<T> findByUserIdsAndPeriod(Collection<UUID> userIds, DateTime from, DateTime to) {
        return userIds.isEmpty() ?
            new ArrayList<>() :
            retrieve(
                entityClass,
                (builder, criteria, root) -> criteria.select(root)
                    .where(root.get("user").get("id").in(userIds),
                        builder.greaterThan(root.get("endDate"), from),
                        builder.lessThan(root.get("startDate"), to))
            );
    }

    @Transactional(readOnly = true)
    public List<T> findAllByHiringDate(Collection<UUID> userIds) {
        return userIds.isEmpty() ?
            new ArrayList<>() : retrieve(
            entityClass,
            (builder, criteria, root) -> criteria.select(root)
                .where(
                    root.get("user").get("id").in(userIds),
                    builder.greaterThan(root.get("endDate"), root.get("user").get("hiringDate")),
                    builder.lessThanOrEqualTo(root.get("startDate"), root.get("user").get("hiringDate"))
                )

        );
    }

    @Transactional(readOnly = true)
    public List<T> findAllByFiringDate(Collection<UUID> userIds) {
        return userIds.isEmpty() ?
            new ArrayList<>() :
            retrieve(
                entityClass,
                (builder, criteria, root) -> criteria.select(root)
                    .where(
                        root.get("user").get("id").in(userIds),
                        builder.greaterThan(root.get("endDate"), root.get("user").get("firingDate")),
                        builder.lessThanOrEqualTo(root.get("startDate"), root.get("user").get("firingDate"))
                    )

            );
    }

    @Transactional(readOnly = true)
    public long countByUserId(UUID userId) {
        return retrieve(entityClass, (builder, criteria, root) ->
            criteria.select(root).where(builder.equal(root.get("user").get("id"), userId))
        ).size();
    }

    public List<T> findAllByUserId(UUID userId) {
        return retrieve(
            entityClass,
            (builder, criteria, root) -> criteria.select(root)
                .where(builder.equal(root.get("user").get("id"), userId))
        );
    }
}
