package com.growth.startupone.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growth.startupone.domain.model.Role;

@Repository
public interface GroupRepository extends JpaRepository<Role, Long>{

}
