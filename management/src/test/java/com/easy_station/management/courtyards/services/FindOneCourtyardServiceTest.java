package com.easy_station.management.courtyards.services;

import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindOneCourtyardServiceTest {
    @InjectMocks
    FindOneCourtyardService findOneCourtyardService;

    @Mock
    CourtyardsRepository courtyardsRepository;

    Courtyard courtyard = new Courtyard("id", "name", 10, "companyId", null, null);

    @Test
    @DisplayName("Should call courtyardRepository and invoke findByAndCompanyId")
    void test1() {
        new CourtyardDTO(courtyard.getId(), courtyard.getName(), courtyard.getMaxCars());
        when(courtyardsRepository.findByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId())).thenReturn(Optional.ofNullable(courtyard));

        CourtyardDTO response = findOneCourtyardService.run(courtyard.getId(), courtyard.getCompanyId());
        verify(courtyardsRepository).findByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId());
        assertThat(response).isInstanceOf(CourtyardDTO.class);
    }

    @Test
    @DisplayName("Should throw NotFoundException when courtyard not exists")
    void test2() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
           findOneCourtyardService.run(courtyard.getId(), courtyard.getCompanyId());
        });

        assertThat(format("Pátio de id %s não existe", "id"));
    }
}