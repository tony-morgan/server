package com.example.security.repositories;

import com.example.security.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by tonym on 20.04.2017.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(@Param("name") String name);
}
