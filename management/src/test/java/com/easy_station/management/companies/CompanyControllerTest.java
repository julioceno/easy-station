package com.easy_station.management.companies;

import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.dto.CreateCompanyDTO;
import com.easy_station.management.companies.dto.UpdateCompanyDTO;
import com.easy_station.management.companies.services.CompanyService;
import com.easy_station.management.companies.services.UpdateCompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {
    @InjectMocks
    CompanyController companyController;

    @Mock
    CompanyService companyService;

    CompanyDTO companyDTO = new CompanyDTO("id", "name", 10.00);

    @Test
    @DisplayName("Should call companyService and invoke create method")
    void test1() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        CreateCompanyDTO dto = new CreateCompanyDTO(companyDTO.getName(), companyDTO.getPriceHour());
        when(companyService.create(any(CreateCompanyDTO.class))).thenReturn(companyDTO);

        companyController.create(dto);

        verify(companyService).create(any(CreateCompanyDTO.class));
    }

    @Test
    @DisplayName("Should call companyService and invoke findOne method")
    void test2() {
        companyController.findOne("id");
        verify(companyService).findOne("id");
    }

    @Test
    @DisplayName("Should call companyService and invoke update method")
    void test3() {
        UpdateCompanyDTO updateCompanyDTO = new UpdateCompanyDTO(companyDTO.getName(), companyDTO.getPriceHour());
        companyController.update("id", updateCompanyDTO);
        verify(companyService).update("id", updateCompanyDTO);
    }

    @Test
    @DisplayName("Should call companyService and invoke delete method")
    void test4() {
        companyController.delete("id");
        verify(companyService).delete("id");
    }

    @Test
    @DisplayName("Should call companyService and invoke findOne method in my company route")
    void test5() {
        companyController.findOneByUser("id");
        verify(companyService).findOne("id");
    }

    @Test
    @DisplayName("Should call companyService and invoke update method in my company route")
    void test6() {
        UpdateCompanyDTO updateCompanyDTO = new UpdateCompanyDTO(companyDTO.getName(), companyDTO.getPriceHour());
        companyController.updateByUser("id", updateCompanyDTO);
        verify(companyService).update("id", updateCompanyDTO);
    }
}