package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import com.easy_station.management.courtyards.dto.UpdateCourtyardDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourtyardsServiceTest {
    @InjectMocks
    CourtyardsService courtyardsService;

    @Mock
    FindOneCourtyardService findOneCourtyardService;

    @Mock
    CreateCourtyardService createCourtyardService;

    @Mock
    FindAllCourtyardsService findAllCourtyardsService;

    @Mock
    DeleteCourtyardService deleteCourtyardService;

    @Mock
    UpdateCourtyardService updateCourtyardService;

    @Test
    @DisplayName("Should call findOneCourtyardService and invoke run method")
    void test1() {
        courtyardsService.findOne("id", "companyId");
        verify(findOneCourtyardService).run("id", "companyId");
    }

    @Test
    @DisplayName("Should call createCourtyardService and invoke run method")
    void test2() {
        CreateCourtyardDTO createCourtyardDTO = new CreateCourtyardDTO("name", 10);
        courtyardsService.create(createCourtyardDTO, "companyId");
        verify(createCourtyardService).run(createCourtyardDTO, "companyId");
    }

    @Test
    @DisplayName("Should call findAllCourtyardsService and invoke run method")
    void test3() {
        courtyardsService.findAll("companyId");
        verify(findAllCourtyardsService).run("companyId");
    }

    @Test
    @DisplayName("Should call deleteCourtyardService and invoke run method")
    void test4() {
        courtyardsService.delete("id", "companyId");
        verify(deleteCourtyardService).run("id", "companyId");
    }

    @Test
    @DisplayName("Should call updateCourtyardService and invoke run method")
    void test5() {
        UpdateCourtyardDTO courtyardDTO = new UpdateCourtyardDTO("name", 10);
        courtyardsService.update("id", courtyardDTO, "companyId");
    }
}