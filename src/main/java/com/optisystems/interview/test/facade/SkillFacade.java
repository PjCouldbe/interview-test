package com.optisystems.interview.test.facade;

import com.optisystems.interview.test.facade.base.FiltratingFacade;
import com.optisystems.interview.test.model.PermissionGroup;
import com.optisystems.interview.test.model.Skill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Service
public class SkillFacade extends FiltratingFacade<Skill> {
    public SkillFacade() {
        super(Skill.class);
    }

    @Transactional(readOnly = true)
    public Skill findByExternalIdOrNull(String externalId) {
        return retrieveOneOrNull(
                Skill.class,
                (builder, criteria, root) -> {

                    Path<String> externalPath = root.get("externalId");

                    criteria
                            .select(root)
                            .where(builder.equal(externalPath, externalId));
                });
    }

    @Override
    @Transactional
    public List<Skill> findByGroups(Collection<PermissionGroup> groups) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(getEntityClass());
        Root<Skill> root = cq.from(getEntityClass());
        Join<Skill, PermissionGroup> joinGroup = root.join("groups", JoinType.LEFT);

        List<UUID> groupList = groups.stream().map(PermissionGroup::getId).collect(toList());
        cq.where(joinGroup.get("id").in(groupList));
        cq.distinct(true);
        cq.select(root);

        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    @Transactional
    public List<Skill> findAllByGroup(PermissionGroup group) {
        return findByGroups( singletonList(group) );
    }

    @Override
    @Transactional(readOnly = true)
    public long countBy(String fieldName, Object value) {
        if (fieldName.equals("group") || fieldName.equals("groups")) {
            return findByGroups( toGroupList(value) ).size();
        } else {
            return super.countBy(fieldName, value);
        }
    }

    @SuppressWarnings("unchecked")
    private List<PermissionGroup> toGroupList(Object value) {
        if (value instanceof PermissionGroup) {
            return singletonList((PermissionGroup) value);
        } else if (value instanceof Collection) {
            Collection<?> c = (Collection<?>) value;
            return c.isEmpty() || c.iterator().next() instanceof PermissionGroup
                ? new ArrayList<>( (Collection<PermissionGroup>) c)
                : emptyList();
        } else {
            throw new IllegalArgumentException( String.format(
                "There is invalid argument provided. Expected of type: '%s' or '%s<%s>'; got: '%s'",
                PermissionGroup.class.getSimpleName(),
                Collection.class.getSimpleName(),
                PermissionGroup.class.getSimpleName(),
                value.getClass().getCanonicalName()
            ) );
        }
    }
}
