package com.easy_station.management.courtyards.database;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "select * from courtyards where name = ?1 and company_id = ?2 and id <> ?3 LIMIT 1", nativeQuery = true)
    Optional<Courtyard> findByNameAndCompanyIdAndNotId(String name, String companyId, String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM courtyards WHERE id = ?1 AND company_id = ?2", nativeQuery = true)
    void deleteByIdAndCompanyId(String id, String companyId);
}
