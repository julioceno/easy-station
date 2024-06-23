package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CalculatemountToChargedDTO;
import com.easy_station.management.cars.dto.CarDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculateAmountToChargedServiceTest {
    @InjectMocks
    CalculateAmountToChargedService calculateAmountToChargedService;

    @Mock
    CarRepository carRepository;

    @Mock
    FindOneCarService findOneCarService;

    Instant instant = Instant.now().plus(Duration.ofHours(-1));
    CarDTO carDTO;

    @BeforeEach
    void setUp() {
        carDTO = new CarDTO("id", "ownerName", "plate", "courtyardId",10.0, Date.from(instant), null);
        when(findOneCarService.run("id", "companyId")).thenReturn(carDTO);
    }

    @Test
    @DisplayName("Should call findOneCarService and invoke run method")
    void test1() {
        calculateAmountToChargedService.run("id", "companyId");
        verify(findOneCarService).run("id", "companyId");
    }

    @Test
    @DisplayName("Should return value '10' when car stays 1 hour in courtyard")
    void test2() {
        CalculatemountToChargedDTO dto = calculateAmountToChargedService.run("id", "companyId");
        assertEquals(dto.value(), "10.00");
    }

    @Test
    @DisplayName("Should return value '11.67' when car stays 1 hour and 10 minutes in courtyard")
    void test3() {
        Instant instant = Instant.now().plus(Duration.ofHours(-1)).plus(Duration.ofMinutes(-10));
        carDTO.setCheckIn(Date.from(instant));
        when(findOneCarService.run("id", "companyId")).thenReturn(carDTO);

        CalculatemountToChargedDTO dto = calculateAmountToChargedService.run("id", "companyId");
        assertEquals(dto.value(), "11.67");
    }
}