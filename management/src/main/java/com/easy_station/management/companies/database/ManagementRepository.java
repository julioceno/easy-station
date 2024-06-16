package com.easy_station.management.companies.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManagementRepository extends JpaRepository<Management, String> {
    Optional<Management> findByCompanyId(String companyId);
}
