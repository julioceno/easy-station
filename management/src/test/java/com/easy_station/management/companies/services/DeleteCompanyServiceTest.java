package com.easy_station.management.companies.services;

import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.companies.domain.Company;
import com.easy_station.management.companies.domain.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCompanyServiceTest {
    @InjectMocks
    DeleteCompanyService deleteCompanyService;

    @Mock
    private CompanyRepository companyRepository;

    Company company = new Company("id", "name", 10.0, null, null, null);

    @BeforeEach
    void setUp() {
        when(companyRepository.existsById("id")).thenReturn(true);
    }

    @Test
    @DisplayName("Should call companyRepository and invoke deleteById")
    void test1() {
        deleteCompanyService.run("id");
        verify(companyRepository).deleteById("id");
    }

    @Test
    @DisplayName("Should call companyRepository and invoke existsById")
    void test2() {
        deleteCompanyService.run("id");
        verify(companyRepository).existsById("id");
    }

    @Test
    @DisplayName("Should throw NotFoundException when company not exists")
    void test3() {
        when(companyRepository.existsById("id")).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            deleteCompanyService.run("id");
        });

        assertEquals(format("Empresa de id %s não existe", "id"), exception.getMessage());
    }
}