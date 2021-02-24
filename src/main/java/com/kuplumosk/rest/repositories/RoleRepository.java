package com.kuplumosk.rest.repositories;

import com.kuplumosk.rest.entitys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
