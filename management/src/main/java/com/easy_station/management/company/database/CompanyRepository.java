package com.easy_station.management.company.database;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
}
