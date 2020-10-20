package com.chrisaraneo.mwl.repository;

import com.chrisaraneo.mwl.model.Role;
import com.chrisaraneo.mwl.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
    Optional<Role> findByName(RoleName roleName);
    
}