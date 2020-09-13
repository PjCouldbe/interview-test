package com.optisystems.interview.test.facade;

import com.optisystems.interview.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAll();
    
}
