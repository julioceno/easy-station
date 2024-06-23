package com.easy_station.management.courtyards.services;

import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.common.exceptions.NotFoundException;
import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import com.easy_station.management.courtyards.dto.UpdateCourtyardDTO;
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
class UpdateCourtyardServiceTest {
    @InjectMocks
    UpdateCourtyardService updateCourtyardService;

    @Mock
    CourtyardsRepository courtyardsRepository;

    Courtyard courtyard;
    CourtyardDTO courtyardDTO;
    UpdateCourtyardDTO updateCourtyardDTO;

    @BeforeEach
    void setUp() {
        courtyard = new Courtyard("id", "name", 10, "companyId", null, null);
        courtyardDTO = new CourtyardDTO(courtyard);
        updateCourtyardDTO = new UpdateCourtyardDTO("new name", 50);

        when(courtyardsRepository.findByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId())).thenReturn(Optional.ofNullable(courtyard));
        when(courtyardsRepository.save(any(Courtyard.class))).thenReturn(courtyard);
    }

    @Test
    @DisplayName("Should call courtyardsRepository and invoke findByIdAndCompanyId method")
    void test1() {
        updateCourtyardService.run("id", updateCourtyardDTO, "companyId");
        verify(courtyardsRepository).findByIdAndCompanyId("id", "companyId");
    }

    @Test
    @DisplayName("Should throw NotFoundException when courtyard not exists")
    void test2() {
        when(courtyardsRepository.findByIdAndCompanyId(courtyard.getId(), courtyard.getCompanyId())).thenReturn(Optional.ofNullable(null));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            updateCourtyardService.run("id", updateCourtyardDTO, "companyId");
        });

        assertEquals(format("Pátio com o id %s não existe", "id"), exception.getMessage());
    }

    @Test
    @DisplayName("Should call courtyardsRepository and invoke findByNameAndCompanyIdAndNotId method")
    void test3() {
        updateCourtyardService.run("id", updateCourtyardDTO, "companyId");
        verify(courtyardsRepository).findByNameAndCompanyIdAndNotId(updateCourtyardDTO.name(), courtyard.getCompanyId(), courtyard.getId());
    }

    @Test
    @DisplayName("Should throw BadRequestException when company with new name already exists")
    void test4() {
        when(courtyardsRepository.findByNameAndCompanyIdAndNotId(updateCourtyardDTO.name(), courtyard.getCompanyId(), courtyard.getId())).thenReturn(Optional.ofNullable(courtyard));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            updateCourtyardService.run("id", updateCourtyardDTO, "companyId");
        });

        assertEquals(format("Nome %s já esta em uso", updateCourtyardDTO.name()), exception.getMessage());
    }

    @Test
    @DisplayName("Should call courtyardsRepository and invoke save method")
    void test5() {
        CourtyardDTO response = updateCourtyardService.run("id", updateCourtyardDTO, "companyId");
        verify(courtyardsRepository).save(any(Courtyard.class));
        assertThat(response).isInstanceOf(CourtyardDTO.class);
    }
}