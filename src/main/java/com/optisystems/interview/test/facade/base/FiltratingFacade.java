package com.optisystems.interview.test.facade.base;


import com.optisystems.interview.test.facade.PermissionGroupFacade;
import com.optisystems.interview.test.model.PermissionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public abstract class FiltratingFacade<Entity> extends AbstractFacade<Entity> {

    @Autowired
    protected PermissionGroupFacade groupFacade;

    public FiltratingFacade(Class<Entity> entityClass) {
        super(entityClass);
    }

    @Transactional(readOnly = true)
    public List<Entity> findAllAvailable(PermissionGroup group) {
        return findByGroups(groupFacade.findAllAvailable(group));
    }

    @Transactional(readOnly = true)
    public List<Entity> findByGroups(Collection<PermissionGroup> groups) {
        CriteriaBuilder cb = getCriteriaBuilder();

        CriteriaQuery<Entity> cq = cb.createQuery(getEntityClass());

        Root<Entity> root = cq.from(getEntityClass());
        cq.where(root.get("group").in(groups));
        cq.select(root);

        TypedQuery<Entity> tq = getEntityManager().createQuery(cq);

        return tq.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Entity> findAllByGroup(PermissionGroup group) {

        CriteriaBuilder cb = getCriteriaBuilder();

        CriteriaQuery<Entity> cq = cb.createQuery(getEntityClass());

        Root<Entity> root = cq.from(getEntityClass());
        cq.where(cb.equal(root.get("group"), group));
        cq.select(root);

        TypedQuery<Entity> tq = getEntityManager().createQuery(cq);

        return tq.getResultList();
    }
}
