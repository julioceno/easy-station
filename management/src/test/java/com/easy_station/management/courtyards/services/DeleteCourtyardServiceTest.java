package com.easy_station.management.courtyards.services;

import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCourtyardServiceTest {
    @InjectMocks
    DeleteCourtyardService deleteCourtyardService;

    @Mock
    CourtyardsRepository courtyardsRepository;

    Courtyard courtyard = new Courtyard("id", "name", 10, "companyId", null, null);

    @BeforeEach
    void setUp() {
        when(courtyardsRepository.findByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId())).thenReturn(Optional.ofNullable(courtyard));
    }

    @Test
    @DisplayName("Should call courtyardRepository and invoke findByIdAndCompanyId")
    void test1() {
        deleteCourtyardService.run(courtyard.getId(), courtyard.getCompanyId());
        verify(courtyardsRepository).findByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId());
    }

    @Test
    @DisplayName("Should throw NotFoundException when courtyard not exists")
    void test2() {
        when(courtyardsRepository.findByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId())).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            deleteCourtyardService.run(courtyard.getId(), courtyard.getCompanyId());
        });

        assertEquals(format("Pátio de id %s não existe", courtyard.getId()), exception.getMessage());
    }

    @Test
    @DisplayName("Should call courtyardRepository and invoke deleteByIdAndCompanyId")
    void test3() {
        deleteCourtyardService.run(courtyard.getId(), courtyard.getCompanyId());
        verify(courtyardsRepository).deleteByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId());
    }
}