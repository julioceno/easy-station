package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.common.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FindOneCarServiceTest {
    @InjectMocks
    FindOneCarService findOneCarService;

    @Mock
    CarRepository carRepository;

    Car car = new Car("id", "ownerName", "plate", 10.0, "courtyardId", null, null);

    @BeforeEach
    void setUp() {
        when(carRepository.findCarByIdAndCompanyId("id","companyId")).thenReturn(Optional.ofNullable(car));
    }

    @Test
    @DisplayName("Should call carRepository and invoke findCarByIdAndCompanyId method")
    void test1() {
        findOneCarService.run("id", "companyId");
        verify(carRepository).findCarByIdAndCompanyId("id", "companyId");
    }

    @Test
    @DisplayName("Should throw NotFoundException when car not found")
    void test2() {
        when(carRepository.findCarByIdAndCompanyId("id","companyId")).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            findOneCarService.run("id", "companyId");
        });

        assertEquals(format("Veículo de id %s não encontrado.", "id"), exception.getMessage());
    }
}