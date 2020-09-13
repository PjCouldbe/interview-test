package com.optisystems.interview.test.facade.base;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Validated
public abstract class AbstractFacade<Entity> extends AbstractReadOnlyFacade<Entity> {

    protected final Class<Entity> entityClass;

    public AbstractFacade(Class<Entity> entityClass) {
        super(entityClass);
        this.entityClass = entityClass;
    }

    @Transactional
    public Entity create(@Valid Entity entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Transactional
    public Collection<Entity> createAll(@Valid Collection<Entity> entities) {
        entities.forEach(entity -> getEntityManager().persist(entity));
        return entities;
    }

    @Override
    @Transactional(readOnly = true)
    public Entity find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entity> findAll() {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Entity> query = builder.createQuery(getEntityClass());

        query.select(query.from(entityClass));

        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entity> findAll(Collection<?> ids) {
        if (ids.isEmpty()) return new ArrayList<>();

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Entity> query = builder.createQuery(getEntityClass());
        Root<Entity> from = query.from(getEntityClass());

        query
            .select(from)
            .where(from.get("id").in(ids));

        return getEntityManager().createQuery(query).getResultList();
    }

    @Transactional
    public void edit(@Valid Entity entity) {
        getEntityManager().merge(entity);
    }

    @Transactional
    public void editAll(@Valid Collection<Entity> entities) {
        entities.forEach(entity -> getEntityManager().merge(entity));
    }

    @Transactional
    public void remove(Entity entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Transactional
    public void removeAll(Collection<Entity> entities) {
        entities.forEach(entity -> getEntityManager().remove(getEntityManager().merge(entity)));
    }

    @Transactional
    public void removeAll() {
        removeAll(findAll());
    }
}
