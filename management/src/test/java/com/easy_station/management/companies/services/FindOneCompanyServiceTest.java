package com.easy_station.management.companies.services;

import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.companies.domain.Company;
import com.easy_station.management.companies.domain.CompanyRepository;
import com.easy_station.management.companies.dto.CompanyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindOneCompanyServiceTest {
    @InjectMocks
    FindOneCompanyService findOneCompanyService;

    @Mock
    CompanyRepository companyRepository;

    Company company = new Company("id", "name", 10.0, null, null, null);

    @BeforeEach
    void setUp() {
        when(companyRepository.findById("id")).thenReturn(Optional.ofNullable(company));
    }

    @Test
    @DisplayName("should call companyRepository and invoke findById")
    void test1() {
        CompanyDTO response = findOneCompanyService.run("id");
        verify(companyRepository).findById("id");
        assertThat(response).isInstanceOf(CompanyDTO.class);
    }

    @Test
    @DisplayName("Should throw NotFoundException when company not exists")
    void test2() {
        when(companyRepository.findById("id")).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            findOneCompanyService.run("id");
        });

        assertEquals(format("Empresa de id %s não existe", "id"), exception.getMessage());
    }
}