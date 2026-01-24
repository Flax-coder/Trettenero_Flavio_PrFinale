package it.aulab.chronicles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.aulab.chronicles.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
