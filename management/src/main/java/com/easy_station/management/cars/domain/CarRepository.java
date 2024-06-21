package com.easy_station.management.cars.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {
    @Query(value = "\n " +
            "select cars.* from cars \n" +
            "join courtyards ON cars.courtyard_id = courtyards.id  \n" +
            "join companies on courtyards.company_id = companies.id \n" +
            "where cars.plate = ?1 and companies.id = ?2 and cars.updated_at is null LIMIT 1" +
            "\n", nativeQuery = true)
    Optional<Car> findActiveCarByPlateAndCompanyId(String plate, String companyId);

    @Query(value = "\n " +
        "select cars.* from cars \n" +
        "join courtyards ON cars.courtyard_id = courtyards.id  \n" +
        "join companies on courtyards.company_id = companies.id \n" +
        "where cars.id = ?1 and companies.id = ?2 LIMIT 1" +
            "\n", nativeQuery = true
    )
    Optional<Car> findCarByIdAndCompanyId(String id, String companyId);

    @Query(value = "\n " +
            "select cars.* from cars \n" +
            "join courtyards ON cars.courtyard_id = courtyards.id  \n" +
            "join companies on courtyards.company_id = companies.id \n" +
            "where companies.id = ?1" +
            "\n", nativeQuery = true
    )
    List<Car> findAllCarsByCompanyId(String companyId);

    @Query(value = "\n " +
            "select cars.* from cars \n" +
            "join courtyards ON cars.courtyard_id = courtyards.id  \n" +
            "join companies on courtyards.company_id = companies.id \n" +
            "where courtyards.id = ?1 and companies.id = ?2" +
            "\n", nativeQuery = true
    )
    List<Car> findAllCarsByCourtyardIdAndCompanyId(String courtyardId, String companyId);
}
