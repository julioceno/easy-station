package com.easy_station.management.courtyards.services;

import com.easy_station.management.courtyards.domain.Courtyard;
import com.easy_station.management.courtyards.domain.CourtyardsRepository;
import com.easy_station.management.courtyards.dto.CourtyardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindAllCourtyardsServiceTest {
    @InjectMocks
    FindAllCourtyardsService findAllCourtyardsService;

    @Mock
    CourtyardsRepository courtyardsRepository;

    Courtyard courtyard = new Courtyard("id", "name", 10, "companyId", null, null);

    @BeforeEach
    void setUp() {
        when(courtyardsRepository.findAllByCompanyId(courtyard.getCompanyId())).thenReturn(Arrays.asList(courtyard));
    }

    @Test
    @DisplayName("Should call courtyardsRepository and invoke findAllByCompanyId")
    void test1() {
        findAllCourtyardsService.run(courtyard.getCompanyId());
        verify(courtyardsRepository).findAllByCompanyId(courtyard.getCompanyId());
    }

    @Test
    @DisplayName("Should mapping courtyards to CourtyardDTO")
    void test2() {
        List<CourtyardDTO> courtyardDTOList =findAllCourtyardsService.run(courtyard.getCompanyId());
        courtyardDTOList.forEach(courtyardDTO -> {
            assertThat(courtyardDTO).isInstanceOf(CourtyardDTO.class);
        });
    }
}