package com.easy_station.management.users.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM users WHERE external_id = ?1 LIMIT 1", nativeQuery = true)
    Optional<User> findByExternalId(String externalId);

    @Query(value = "SELECT * FROM users WHERE email = ?1 LIMIT 1", nativeQuery = true)
    Optional<User> findByEmail(String email);
}
