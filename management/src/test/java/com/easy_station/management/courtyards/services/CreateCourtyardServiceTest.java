package com.easy_station.management.courtyards.services;

import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.companies.domain.Company;
import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.CreateCourtyardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreateCourtyardServiceTest {
    @InjectMocks
    CreateCourtyardService createCourtyardService;

    @Mock
    CourtyardsRepository courtyardsRepository;

    Courtyard courtyard = new Courtyard("id", "name", 10, "companyId", null, null);
    CreateCourtyardDTO createCourtyardDTO = new CreateCourtyardDTO("name", 10);

    @BeforeEach
    void setUp() {
        when(courtyardsRepository.save(any(Courtyard.class))).thenReturn(courtyard);
    }

    @Test
    @DisplayName("Should call courtyardsRepository and invoke findByNameAndCompanyId method")
    void test1() {
        createCourtyardService.run(createCourtyardDTO, courtyard.getCompanyId());
        verify(courtyardsRepository).findByNameAndCompanyId(courtyard.getName(), courtyard.getCompanyId());
    }

    @Test
    @DisplayName("Should throw BadRequestException when courtyard not exists")
    void test2() {
        when(courtyardsRepository.findByNameAndCompanyId(courtyard.getName(), courtyard.getCompanyId())).thenReturn(Optional.ofNullable(courtyard));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            createCourtyardService.run(createCourtyardDTO, courtyard.getCompanyId());
        });
        assertEquals(format("Pátio com nome %s já existe para essa empresa.", courtyard.getName()), exception.getMessage());
    }

    @Test
    @DisplayName("Should call courtyardsRepository and invoke save method")
    void test3() {
        CourtyardDTO courtyardDTO = createCourtyardService.run(createCourtyardDTO, courtyard.getCompanyId());

        verify(courtyardsRepository).save(any(Courtyard.class));
        assertThat(courtyardDTO).isInstanceOf(CourtyardDTO.class);
    }
}