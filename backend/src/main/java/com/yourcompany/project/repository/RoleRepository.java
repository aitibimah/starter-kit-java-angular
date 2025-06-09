package com.yourcompany.project.repository;

import com.yourcompany.project.domain.Role;
import com.yourcompany.project.domain.Role.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
    Boolean existsByName(ERole name);
}