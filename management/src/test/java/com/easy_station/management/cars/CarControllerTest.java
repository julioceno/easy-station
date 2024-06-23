package com.easy_station.management.cars;

import com.easy_station.management.cars.dto.CarDTO;
import com.easy_station.management.cars.dto.CreateCarDTO;
import com.easy_station.management.cars.services.CarsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {
    @InjectMocks
    CarController carController;

    @Mock
    CarsService carsService;

    CarDTO carDTO = new CarDTO("id", "ownerName", "plate", "courtyardId",10.0, null, null);

    @Test
    @DisplayName("Should call carsService and invoke checkIn method")
    void test1() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CreateCarDTO createCarDTO = new CreateCarDTO("ownerName", "plate", "courtyardId");
        when(carsService.checkIn(createCarDTO,"companyId")).thenReturn(carDTO);

        carController.checkIn("companyId", createCarDTO);
        verify(carsService).checkIn(createCarDTO, "companyId");
    }

    @Test
    @DisplayName("Should call carsService and invoke checkOut method")
    void test2() {
        carController.checkOut("companyId", "id" );
        verify(carsService).checkOut("id", "companyId");
    }

    @Test
    @DisplayName("Should call carsService and invoke findAll method")
    void test3() {
        carController.findAll("companyId");
        verify(carsService).findAll("companyId");
    }

    @Test
    @DisplayName("Should call carsService and invoke findOne method")
    void test4() {
        carController.findOne("companyId", "id");
        verify(carsService).findOne("id", "companyId");
    }

    @Test
    @DisplayName("Should call carsService and invoke calculateAmountToCharged method")
    void  test5() {
        carController.calculatePrice("companyId", "id");
        verify(carsService).calculateAmountToCharged("id", "companyId");
    }
}
