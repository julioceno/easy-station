package com.easy_station.management.companies.services;

import com.easy_station.management.companies.dto.CreateCompanyDTO;
import com.easy_station.management.companies.dto.UpdateCompanyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @InjectMocks
    CompanyService companyService;

    @Mock
    CreateCompanyService createCompanyService;

    @Mock
    FindOneCompanyService findOneCompanyService;

    @Mock
    UpdateCompanyService updateCompanyService;

    @Mock
    DeleteCompanyService deleteCompanyService;

    @Test
    @DisplayName("Should call findOneCompanyService and invoke run method")
    void test1() {
        companyService.findOne("id");
        verify(findOneCompanyService).run("id");
    }

    @Test
    @DisplayName("Should call createCompanyService and invoke run method")
    void test2() {
        CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO("name", 10.00);
        companyService.create(createCompanyDTO);
        verify(createCompanyService).run(createCompanyDTO);
    }

    @Test
    @DisplayName("Should call updateCompanyService and invoke run method")
    void test3() {
        UpdateCompanyDTO updateCompanyDTO = new UpdateCompanyDTO("name", 10.00);
        companyService.update("id", updateCompanyDTO);
        verify(updateCompanyService).run("id", updateCompanyDTO);
    }

    @Test
    @DisplayName("Should call deleteCompanyService and invoke run method")
    void test4() {
        deleteCompanyService.run("id");
        verify(deleteCompanyService).run("id");
    }
}