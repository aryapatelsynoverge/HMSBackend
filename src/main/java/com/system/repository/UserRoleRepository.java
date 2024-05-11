package com.system.repository;

import com.system.model.Role;
import com.system.model.User;
import com.system.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByRole(Role role);
    UserRole findByUser(User user);
}
