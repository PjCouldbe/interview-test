package com.optisystems.interview.test.facade;

import com.optisystems.interview.test.facade.base.AbstractFacade;
import com.optisystems.interview.test.model.PermissionGroup;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class PermissionGroupFacade extends AbstractFacade<PermissionGroup> {
    public PermissionGroupFacade() {
        super(PermissionGroup.class);
    }

    public List<PermissionGroup> findByParent(PermissionGroup parent) {
        CriteriaBuilder cb = getCriteriaBuilder();

        CriteriaQuery<PermissionGroup> cq = cb.createQuery(PermissionGroup.class);

        Root<PermissionGroup> root = cq.from(PermissionGroup.class);
        cq.where(cb.equal(root.get("parent"), parent));
        cq.select(root);

        TypedQuery<PermissionGroup> tq = getEntityManager().createQuery(cq);

        return tq.getResultList();
    }

    public List<PermissionGroup> findAllAvailable(PermissionGroup group) {
        List<PermissionGroup> result = new ArrayList<>();
        result.add(group);
        ArrayDeque<PermissionGroup> stack = new ArrayDeque<>();
        stack.add(group);

        while (!stack.isEmpty()) {
            List<PermissionGroup> temp = findByParent(stack.pop());
            result.addAll(temp);
            stack.addAll(temp);
        }

        return result;
    }
    
}
