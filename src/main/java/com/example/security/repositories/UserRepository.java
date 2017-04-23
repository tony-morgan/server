package com.example.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.example.security.models.User;

/**
 * Created by an on 19.04.2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(@Param("email") String email);
    
    User findByUsername(@Param("username") String username);
}
