package com.easy_station.management.cars.services;

import com.easy_station.management.cars.domain.Car;
import com.easy_station.management.cars.domain.CarRepository;
import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.services.FindOneCourtyardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateLimitCarsInCourtyardServiceTest {
    @InjectMocks
    ValidateLimitCarsInCourtyardService validateLimitCarsInCourtyardService;

    @Mock
    FindOneCourtyardService findOneCourtyardService;

    @Mock
    CarRepository carRepository;

    CourtyardDTO courtyardDTO = new CourtyardDTO("id", "name", 10);
    Car car = new Car("id", "ownerName", "plate", 10.0, "courtyardId", null, null);

    @BeforeEach
    void setUp() {
        when(findOneCourtyardService.run("courtyardId", "companyId")).thenReturn(courtyardDTO);
    }

    @Test
    @DisplayName("Should call findOneCourtyardService and invoke run method")
    void test1() {
        validateLimitCarsInCourtyardService.run("courtyardId", "companyId");
        verify(findOneCourtyardService).run("courtyardId", "companyId");
    }

    @Test
    @DisplayName("Should call carRepository and invoke findAllCarsByCourtyardIdAndCompanyId method")
    void test2() {
        validateLimitCarsInCourtyardService.run("courtyardId", "companyId");
        verify(carRepository).findAllCarsByCourtyardIdAndCompanyId("courtyardId", "companyId");
    }

    @Test
    @DisplayName("should throw BadRequestException when has more cars than limit cars by courtyard")
    void test3() {
        when(carRepository.findAllCarsByCourtyardIdAndCompanyId("courtyardId", "companyId")).thenReturn(Arrays.asList(car, car, car, car, car, car, car, car, car, car));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            validateLimitCarsInCourtyardService.run("courtyardId", "companyId");
        });

        assertEquals("Não é possível seguir com a operação pois o patio em questão ja está cheio.", exception.getMessage());
    }
}