package com.epam.esm.repositories;

import com.epam.esm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author orest uzhytchak
 * A repository layer for user class
 * */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
