package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindAllCarsServiceTest {
    @InjectMocks
    FindAllCarsService findAllCarsService;

    @Mock
    CarRepository carRepository;

    Car car = new Car("id", "ownerName", "plate", 10.0, "courtyardId", null, null);

    @BeforeEach
    void setUp() {
        when(carRepository.findAllCarsByCompanyId("companyId")).thenReturn(Arrays.asList(car));
    }

    @Test
    @DisplayName("Should call carRepository and invoke findAllCarsByCompanyId")
    void test1() {
        findAllCarsService.run("companyId");
        verify(carRepository).findAllCarsByCompanyId("companyId");
    }

    @Test
    @DisplayName("Should mapping cars to CarDTO")
    void test2() {
        List<CarDTO> carsDTOList =findAllCarsService.run("companyId");
        carsDTOList.forEach(carDTO -> {
            assertThat(carDTO).isInstanceOf(CarDTO.class);
        });
    }
}