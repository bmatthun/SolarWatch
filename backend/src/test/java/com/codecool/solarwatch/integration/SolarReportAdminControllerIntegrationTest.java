package com.codecool.solarwatch.integration;

import com.codecool.solarwatch.SolarWatchApplication;
import com.codecool.solarwatch.configuration.WebClientConfiguration;
import com.codecool.solarwatch.controller.SolarReportAdminController;
import com.codecool.solarwatch.model.SolarReport;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.codecool.solarwatch.service.SunriseSunsetService;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SolarWatchApplication.class })
@WebAppConfiguration
public class SolarReportAdminControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Mock
    private SunriseSunsetService sunriseSunsetService;

    @InjectMocks
    private SolarReportAdminController solarReportAdminController;

    @MockBean
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new SolarReportAdminController(sunriseSunsetService)).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesSolarReportAdminController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertNotNull(webApplicationContext.getBean(SolarReportAdminController.class));
    }

    @WithMockUser(roles = "ADMIN") // This annotation simulates an authenticated user with ADMIN role
    @Test
    public void testAddSolarReport_Success() throws Exception {
        SolarReport mockReport = new SolarReport(LocalDate.now(), "12:12:12 AM", "12:12:12 PM", "Budapest" ); // Create a mock report as needed
        when(sunriseSunsetService.addSolarReport("Budapest", LocalDate.now())).thenReturn(mockReport);

        this.mockMvc.perform(post("/api/admin/add")
                        .param("city", "Budapest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAddSolarReport_NoAuth_Failure() throws Exception {
        this.mockMvc.perform(post("/api/admin/add")
                        .param("city", "Budapest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
