package com.easy_station.management.companies.services;

import com.easy_station.management.companies.domain.Company;
import com.easy_station.management.companies.domain.CompanyRepository;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.dto.CreateCompanyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateCompanyServiceTest {
    @InjectMocks
    CreateCompanyService createCompanyService;

    @Mock
    CompanyRepository companyRepository;

    Company company = new Company("id", "name", 10.0, null, null, null);
    CompanyDTO companyDTO = new CompanyDTO(company);
    CreateCompanyDTO createCompanyDTO = new CreateCompanyDTO(company.getName(), company.getPriceHour());
    @BeforeEach
    void setUp() {
        when(companyRepository.save(any(Company.class))).thenReturn(company);
    }

    @Test
    @DisplayName("Should return company")
    void test1() {
        CompanyDTO response = createCompanyService.run(createCompanyDTO);
        assertThat(response).isInstanceOf(CompanyDTO.class);
    }

    @Test
    @DisplayName("Should call companyRepository and invoke save method")
    void test2() {
        createCompanyService.run(createCompanyDTO);
        verify(companyRepository).save(any(Company.class));
    }
}