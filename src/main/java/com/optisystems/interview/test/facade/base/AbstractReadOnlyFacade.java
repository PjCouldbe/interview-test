package com.optisystems.interview.test.facade.base;

import com.optisystems.interview.test.model.base.AbstractBaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractReadOnlyFacade<Entity> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<Entity> entityClass;

    protected List<Entity> allEntities;

    public AbstractReadOnlyFacade(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional(readOnly = true)
    public Entity find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Entity> findAll() {

        if (allEntities == null || allEntities.isEmpty()) {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            allEntities = getEntityManager().createQuery(cq).getResultList();
        }

        return allEntities;
    }

    @Transactional(readOnly = true)
    public List<Entity> findAll(Collection<?> ids) {
        return findAll().stream().filter(e -> ids.contains(((AbstractBaseEntity) e).getPK())).collect(Collectors.toList());
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void clear() {
        entityManager.clear();
    }

    public void flush() {
        entityManager.flush();
    }
    
    public long countBy(String fieldName, Object value) {
        CriteriaQuery<Long> cq = getEntityManager().getCriteriaBuilder().createQuery(Long.class);
        Root<Entity> rt = cq.from(entityClass);
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        cq.select(criteriaBuilder.count(rt));
        cq.where(
            criteriaBuilder.equal(rt.get(fieldName), value)
        );
        TypedQuery<Long> q = getEntityManager().createQuery(cq);
        return q.getSingleResult();
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }

    @Transactional
    protected <What, From> List<What> retrieve(
        Class<What> whatClass,
        Class<From> fromClass,
        QueryBuilder<What, From> queryBuilder
    ) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<What> criteria = builder.createQuery(whatClass);
        Root<From> root = criteria.from(fromClass);

        queryBuilder.buildQuery(builder, criteria, root);

        return entityManager.createQuery(criteria).getResultList();
    }

    protected <T> List<T> retrieve(Class<T> clazz, QueryBuilder<T, T> queryBuilder) {
        return retrieve(clazz, clazz, queryBuilder);
    }

    protected <What, From> What retrieveOneOrNull(
        Class<What> whatClass,
        Class<From> fromClass,
        QueryBuilder<What, From> queryBuilder
    ) {
        List<What> entities = retrieve(whatClass, fromClass, queryBuilder);

        return entities.size() != 1
            ? null
            : entities.get(0);
    }

    protected <T> T retrieveOneOrNull(Class<T> clazz, QueryBuilder<T, T> queryBuilder) {
        return retrieveOneOrNull(clazz, clazz, queryBuilder);
    }
    
    public Class<Entity> getEntityClass() {
        return entityClass;
    }

    @FunctionalInterface
    protected interface QueryBuilder<What, From> {
        void buildQuery(CriteriaBuilder builder, CriteriaQuery<What> criteria, Root<From> root);
    }
}
