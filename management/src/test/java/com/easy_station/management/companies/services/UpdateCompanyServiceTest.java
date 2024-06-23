package com.easy_station.management.companies.services;

import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.companies.domain.Company;
import com.easy_station.management.companies.domain.CompanyRepository;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.dto.UpdateCompanyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCompanyServiceTest {
    @InjectMocks
    UpdateCompanyService updateCompanyService;

    @Mock
    CompanyRepository companyRepository;

    Company company;
    UpdateCompanyDTO updateCompanyDTO = new UpdateCompanyDTO("new name", 20.00);

    @BeforeEach
    void setUp() {
        company = new Company("id", "name", 10.0, null, null, null);
        when(companyRepository.findById("id")).thenReturn(Optional.ofNullable(company));
    }

    @Test
    @DisplayName("Should call companyRepository and invoke findById method")
    void test1() {
        when(companyRepository.save(company)).thenReturn(company);
        updateCompanyService.run("id", updateCompanyDTO);
        verify(companyRepository).findById("id");
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not exists")
    void test2() {
        when(companyRepository.findById("id")).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            updateCompanyService.run("id", updateCompanyDTO);
        });

        assertEquals(format("Empresa de id %s não existe", "id"), exception.getMessage());
    }

    @Test
    @DisplayName("Should call companyRepository and invoke save method")
    void test3() {
        company.setName(updateCompanyDTO.name());
        company.setPriceHour(updateCompanyDTO.priceHour());

        when(companyRepository.save(company)).thenReturn(company);
        updateCompanyService.run("id", updateCompanyDTO);

        verify(companyRepository).save(company);
    }

    @Test
    @DisplayName("Should return company in CompanyDTO pattern")
    void test4() {
        when(companyRepository.save(company)).thenReturn(company);
        CompanyDTO response = updateCompanyService.run("id", updateCompanyDTO);
        assertThat(response).isInstanceOf(CompanyDTO.class);
    }
}