package com.easy_station.management.cars.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {
    @Query("\n " +
            "select * from cars \n" +
            "join courtyards ON cars.courtyard_id = courtyards.id \n" +
            "join companies on courtyards.company_id = companies.id\n" +
            "where cars.plate = ?1 and companies.id = ?2" +
            "\n")
    Optional<Car> findActiveCarByPlateAndCompanyId(String plate, String companyId);
}
