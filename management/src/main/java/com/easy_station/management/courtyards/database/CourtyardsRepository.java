package com.easy_station.management.courtyards.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourtyardsRepository extends JpaRepository<Courtyard, String> {
    @Query(value = "select * from courtyards where id = ? and company_id = ? LIMIT 1;")
    Optional<Courtyard> findByIdAndCompanyId(String id, String companyId);

    @Query(value = "select * from courtyards where name = ? and company_id = ? LIMIT 1;")
    Optional<Courtyard> findByNameAndCompanyId(String name, String companyId);

    @Query(value = "select * from courtyards where company_id = ?;")
    List<Courtyard> findAllByCompanyId(String companyId);
}
