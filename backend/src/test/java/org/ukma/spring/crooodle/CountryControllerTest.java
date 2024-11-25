package org.ukma.spring.crooodle;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.ukma.spring.crooodle.controller.CountryController;
import org.ukma.spring.crooodle.dto.CountryCriteriaDto;
import org.ukma.spring.crooodle.dto.CountryResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.CountryService;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * testGetCountry:
 * Verifies the GET /countries/{id} endpoint
 * returns the correct country details
 *
 *
 * testGetCountryNotFound:
 * Checks error scenario for invalid country ID
 *
 *
 * testListCountries:
 * Tests the GET /countries endpoint WITHOUT filter
 *
 *
 * testListCountriesWithFilter:
 * Tests the GET /countries endpoint WITH filter.
 */

@WebMvcTest(CountryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CountryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userservice;

    @MockBean
    private CountryService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PaginationDto pagination;

    private CountryResponseDto countryResponseDto;

    private PageResponseDto<CountryResponseDto> pageResponseDto;

    @Mock
    private CountryCriteriaDto criteriaDto;

    @BeforeEach
    void setUp() {

        countryResponseDto = CountryResponseDto
            .builder()
            .id(1)
            .name("TestCountry")
            .build();


        pageResponseDto = PageResponseDto
            .<CountryResponseDto>
                builder()
            .items(Collections.singletonList(countryResponseDto))
            .total(1L)
            .totalPages(1)
            .build();


    }

    @Test
    void testGetCountry() throws Exception {

        when(
            service.get(1)
        )
        .thenReturn(
            countryResponseDto
        );


        mockMvc.perform(
                get("/countries/1")
                .contentType(MediaType.APPLICATION_JSON)
                )

                .andExpect(status().isOk())
                .andExpect(
                    jsonPath("$.id")
                    .value(1)
                )
                .andExpect(
                    jsonPath("$.name")
                    .value("TestCountry")
                );

    }

    @Test
    void testGetCountryNotFound() throws Exception {

        when(
            service.get(99)
        )
        .thenThrow(
            new RuntimeException("Country not found")
        );


        mockMvc.perform(
                get("/countries/99")
                .contentType(MediaType.APPLICATION_JSON)
                )

                .andExpect(status().isInternalServerError())
                .andExpect(
                    content()
                    .string("{\"message\":\"Country not found\"}")
                );

    }

    @Test
    @Disabled("Business logic is not implemented by yet")
    // TODO: implement business logic and make sure it passes the test
    void testListCountries() throws Exception {

        when(
            service.list(any(CountryCriteriaDto.class))
        )
        .thenReturn(
            pageResponseDto
        );


        mockMvc.perform(
                get("/countries")
                .contentType(MediaType.APPLICATION_JSON)
                )

                .andExpect(status().isOk())
                .andExpect(
                    jsonPath("$.items[0].id")
                    .value(1)
                )
                .andExpect(
                    jsonPath("$.items[0].name")
                    .value("TestCountry")
                )
                .andExpect(
                    jsonPath("$.totalElements")
                    .value(1)
                )
                .andExpect(
                    jsonPath("$.totalPages")
                    .value(1)
                );

    }

    @Test
    @Disabled("Business logic is not implemented by yet")
    // TODO: implement business logic and make sure it passes the test
    void testListCountriesWithFilter() throws Exception {

        when(
            service
            .list(any(CountryCriteriaDto.class)))
        .thenReturn(
            pageResponseDto
        );


        mockMvc.perform(
                get("/countries")
                .param(
                    "name",
                    "Test")
                .contentType(MediaType.APPLICATION_JSON)
                )

                .andExpect(status().isOk())
                .andExpect(
                    jsonPath("$.items[0].id")
                    .value(1))
                .andExpect(
                    jsonPath("$.items[0].name")
                    .value("TestCountry"))
                .andExpect(
                    jsonPath("$.totalElements")
                    .value(1))
                .andExpect(
                    jsonPath("$.totalPages")
                    .value(1)
                );

    }
}



