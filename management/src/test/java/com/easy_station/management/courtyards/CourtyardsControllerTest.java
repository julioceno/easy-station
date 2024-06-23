package com.easy_station.management.courtyards;

import com.easy_station.management.common.services.GetCompanyIdByTokenService;
import com.easy_station.management.companies.dto.CreateCompanyDTO;
import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import com.easy_station.management.courtyards.dto.UpdateCourtyardDTO;
import com.easy_station.management.courtyards.services.CourtyardsService;
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
class CourtyardsControllerTest {
    @InjectMocks
    CourtyardsController courtyardsController;

    @Mock
    CourtyardsService courtyardsService;

    Courtyard courtyard = new Courtyard("id", "name", 10, "companyId", null, null);

    @Test
    @DisplayName("Should call courtyardsService and invoke findAll method")
    void test1() {
        courtyardsController.findAll("companyId");
        verify(courtyardsService).findAll("companyId");
    }

    @Test
    @DisplayName("Should call courtyardsService and invoke findOne meethod")
    void test2() {
        courtyardsController.findOne("companyId", "id");
        verify(courtyardsService).findOne("id", "companyId");
    }

    @Test
    @DisplayName("Should call courtyardsService and invoke create method")
    void test3() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CreateCourtyardDTO createCourtyardDTO = new CreateCourtyardDTO(courtyard.getName(), courtyard.getMaxCars());
        CourtyardDTO courtyardDTO = new CourtyardDTO(courtyard);
        when(courtyardsService.create(any(CreateCourtyardDTO.class), any(String.class))).thenReturn(courtyardDTO);

        courtyardsController.create("companyId", createCourtyardDTO);
        verify(courtyardsService).create(createCourtyardDTO, "companyId");
    }

    @Test
    @DisplayName("Should call courtyardsService and invoke update method")
    void test4() {
        UpdateCourtyardDTO createCourtyardDTO = new UpdateCourtyardDTO(courtyard.getName(), courtyard.getMaxCars());
        courtyardsController.update("companyId", "id", createCourtyardDTO);
        verify(courtyardsService).update("id", createCourtyardDTO, "companyId");
    }

    @Test
    @DisplayName("Should call courtyardsService and invoke delete method")
    void test5() {
        courtyardsController.delete("companyId", "id");
        verify(courtyardsService).delete("id", "companyId");
    }
}