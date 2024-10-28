package org.ukma.spring.crooodle.securityTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.ukma.spring.crooodle.controller.HomeController;
import org.ukma.spring.crooodle.service.HotelService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class LoadHomePageSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;


    // AVAILABILITY TESTS

    @Test
    @DisplayName("available for anonymous user")
    @WithAnonymousUser
    void AnonymousUserAvailable() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("available for regular user")
    @WithMockUser(
            username = "user",
            roles = {"USER"}
    )
    void RegularUserAvailable() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("available for admin user")
    @WithMockUser(
            username = "admin",
            roles = {"ADMIN"}
    )
    void AdminUserAvailable() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }




    // AUTHORIZATION TESTS

    @Test
    @DisplayName("only login and register links for anonymous user")
    @WithAnonymousUser
    void whenAnonymousUser_thenShowsLoginAndRegisterLinks() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("You are not logged in. Please log in or register.")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("log in")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("register")));
    }

    @Test
    @DisplayName("no hotels list for unauthenticated user")
    @WithAnonymousUser
    void whenAnonymousUser_thenNoShowsHotelsList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                // authorised-only features
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Available Hotels")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Hotel 1")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Hotel 2")));
    }

    @Test
    @DisplayName("hotels list for authenticated user")
    @WithMockUser(
            username = "user",
            roles = {"USER"}
    )
    void whenAuthenticatedUser_thenShowsHotelsList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                // authorised-only features
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Available Hotels")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Hotel 1")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Hotel 2")));
    }

    @Test
    @DisplayName("no admin section for authenticated user without admin role")
    @WithMockUser(
            username = "user",
            roles = {"USER"}
    )
    void whenAuthenticatedUserWithoutAdminRole_thenNoAdminSection() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                // authorised-only features
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Available Hotels")))
                // admin-only features
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("Admin Dashboard"))))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("Add New Hotel"))))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("Admin Settings"))));
    }


    @Test
    @DisplayName("admin section for admin role")
    @WithMockUser(
            username = "admin",
            roles = {"ADMIN"}
    )
    void whenAuthenticatedAdminUser_thenShowsAdminSection() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                // admin-only features
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Admin Dashboard")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Add New Hotel")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Admin Settings")));
    }
}


