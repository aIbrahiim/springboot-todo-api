package com.woodyinho.spring.repository;

import com.woodyinho.spring.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {

    AppUser findByUsername(String username);
}
