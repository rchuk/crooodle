package org.ukma.spring.crooodle.components.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.ukma.spring.crooodle.controller.admin.CountryAdminController;
import org.ukma.spring.crooodle.dto.CountryCreateRequestDto;
import org.ukma.spring.crooodle.dto.CountryEditRequestDto;
import org.ukma.spring.crooodle.dto.CountryAdminResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.CountryService;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryAdminController.class)
class CountryAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CountryService countryService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    private CountryCreateRequestDto createRequestDto;
    private CountryEditRequestDto editRequestDto;

    private CountryAdminResponseDto responseDto;

    private PageResponseDto<CountryAdminResponseDto> pageResponseDto;

    @BeforeEach
    void setUp() {

        createRequestDto = CountryCreateRequestDto
            .builder()
            .name("TestCountry")
            .build();

        editRequestDto = CountryEditRequestDto
            .builder()
            .name("TestCountry")
            .build();

        responseDto = CountryAdminResponseDto
            .builder()
            .id(1)
            .name("TestCountry")
            .build();

        pageResponseDto = PageResponseDto
            .<CountryAdminResponseDto>builder()
            .build();


    }

    //TODO: piece of shit N_1
    @Test
    @WithMockUser(authorities = "COUNTRY_CREATE")
    void createCountryAuthorized() throws Exception {

        when(
            countryService
            .create(createRequestDto)
        )
        .thenReturn(1);


        mockMvc.perform(

            post("/admin/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createRequestDto))

            )

            .andExpect(
                status()
                .isOk()
            )

            .andExpect(
                content()
                .string("1")
            );


        verify(
            countryService
        )
        .create(
            createRequestDto
        );


    }

    @Test
    void createCountryUnauthorized_ExpectIsForbidden() throws Exception {

        mockMvc.perform(

            post("/admin/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createRequestDto))

            )

            .andExpect(
                status()
                .isForbidden()
            );


        verifyNoInteractions(
            countryService
        );



    }

    @Test
    @WithMockUser(authorities = "COUNTRY_VIEW")
    void getCountryAuthorized() throws Exception {

        when(
            countryService
            .getAdmin(1)
        )
        .thenReturn(
            responseDto
        );


        mockMvc.perform(
            get("/admin/countries/1")
            )

            .andExpect(
                status()
                .isOk()
            )

            .andExpect(
                jsonPath("$.id")
                .value(1)
            )

            .andExpect(
                jsonPath("$.name")
                .value("TestCountry")
            );



        verify(
            countryService
        )
        .getAdmin(
            1
        );


    }

    @Test
    void getCountryUnauthorized_ExpectIsUnauthorized() throws Exception {

        mockMvc.perform(
            get("/admin/countries/1")
            )
            .andExpect(
                status()
                .isUnauthorized()
            );


        verifyNoInteractions(
            countryService
        );


    }

    //TODO: piece of shit N_2
    @Test
    @WithMockUser(authorities = "COUNTRY_EDIT")
    void editCountryAuthorized() throws Exception {

        mockMvc.perform(
            put("/admin/countries/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(editRequestDto))
            )
            .andExpect(
                status()
                .isOk()
            );


        verify(
            countryService
        )
        .edit(
            1,
            editRequestDto
        );
    }

    @Test
    void editCountryUnauthorized_ExpectIsForbidden() throws Exception {

        mockMvc.perform(

            put("/admin/countries/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(editRequestDto))

            )

            .andExpect(
                status()
                .isForbidden()
            );


        verifyNoInteractions(
            countryService
        );



    }

    //TODO: piece of shit N_3
    @Test
    @WithMockUser(authorities = "COUNTRY_DELETE")
    void deleteCountryAuthorized() throws Exception {

        mockMvc.perform(
            delete("/admin/countries/1")
            )
            .andExpect(
                status()
                .isOk()
            );


        verify(
            countryService
        )
        .delete(
            1
        );



    }

    @Test
    void deleteCountryUnauthorized_ExpectIsForbidden() throws Exception {

        mockMvc.perform(
            delete("/admin/countries/1")
            )

            .andExpect(
                status()
                .isForbidden()
            );


        verifyNoInteractions(
            countryService
        );


    }

    @Test
    @WithMockUser(authorities = "COUNTRY_VIEW")
    void listCountriesAuthorized() throws Exception {

        when(
            countryService
            .listAdmin(null)
        )
        .thenReturn(
            pageResponseDto
        );


        mockMvc.perform(
            get("/admin/countries")
            )
            .andExpect(
                status()
                .isOk()
            );

        verify(
            countryService
        )
        .listAdmin(
            null
        );


    }

    @Test
    void listCountriesUnauthorized_ExpectIsUnauthorized() throws Exception {

        mockMvc.perform(
            get("/admin/countries")
            )
            .andExpect(
                status()
                .isUnauthorized()
            );


        verifyNoInteractions(
            countryService
        );


    }
}
