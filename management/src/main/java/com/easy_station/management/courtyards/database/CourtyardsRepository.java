package com.easy_station.management.courtyards.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourtyardsRepository extends JpaRepository<Courtyard, String> {
    @Query(value = "select * from courtyards where id = ?1 and company_id = ?2 LIMIT 1", nativeQuery = true)
    Optional<Courtyard> findByIdAndCompanyId(String id, String companyId);

    @Query(value = "select * from courtyards where name = ?1 and company_id = ?2 LIMIT 1", nativeQuery = true)
    Optional<Courtyard> findByNameAndCompanyId(String name, String companyId);

    @Query(value = "select * from courtyards where company_id = ?1", nativeQuery = true)
    List<Courtyard> findAllByCompanyId(String companyId);
}
