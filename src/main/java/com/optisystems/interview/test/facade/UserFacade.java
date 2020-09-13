package com.optisystems.interview.test.facade;

import com.optisystems.interview.test.facade.base.AbstractFacade;
import com.optisystems.interview.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service("UserFacade")
public class UserFacade extends AbstractFacade<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserFacade(
        UserRepository userRepository
    ) {
        super(User.class);
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(Collection<?> ids) {
        if (CollectionUtils.isEmpty(ids)) return Collections.emptyList();

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        criteriaQuery.select(userRoot);
        criteriaQuery.where(userRoot.get("id").in(ids));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    @Override
    public void editAll(Collection<User> users) {
        userRepository.save(users);
    }

    @Override
    public Collection<User> createAll(Collection<User> users) {
        return userRepository.save(users);
    }
}
