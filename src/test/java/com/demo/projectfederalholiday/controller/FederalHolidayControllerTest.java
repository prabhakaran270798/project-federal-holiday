/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package src.test.java.com.demo.projectfederalholiday.controller;

import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayRequest;
import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayResponse;
import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import src.main.java.com.demo.projectfederalholiday.exception.HolidayNotFoundException;
import src.main.java.com.demo.projectfederalholiday.service.FederalHolidayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Prabhakaran
 */
@WebMvcTest(FederalHolidayController.class)
public class FederalHolidayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HolidayService holidayService;

    @Test
    void shouldGetAllHolidays() throws Exception {
        FederalHolidayResponse response = FederalHolidayResponse.builder()
                .id(1L)
                .country(FederalCountry.USA)
                .name("Christmas")
                .date(LocalDate.of(2026, 12, 25))
                .build();
        when(holidayService.getAllHolidays()).thenReturn(List.of(response));
        mockMvc.perform(get("/api/holidays"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldCreateHoliday() throws Exception {
        FederalHolidayRequest request = new FederalHolidayRequest(FederalCountry.USA, "Christmas", LocalDate.of(2026, 12, 25));
        FederalHolidayResponse response = FederalHolidayResponse.builder()
                .id(1L)
                .country(FederalCountry.USA)
                .name("Christmas")
                .date(LocalDate.of(2026, 12, 25))
                .build();
        when(holidayService.addHoliday(any())).thenReturn(response);
        mockMvc.perform(post("/api/holidays")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetHolidaysByCountry() throws Exception {
        FederalHolidayResponse response = FederalHolidayResponse.builder()
                .id(1L)
                .country(FederalCountry.USA)
                .name("Independence Day")
                .date(LocalDate.of(2026, 7, 4))
                .build();
        when(holidayService.getHolidaysByCountry(FederalCountry.USA)).thenReturn(List.of(response));
        mockMvc.perform(get("/api/holidays/country/USA"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateHoliday() throws Exception {
        FederalHolidayRequest request = new FederalHolidayRequest(FederalCountry.USA, "Updated Holiday", LocalDate.of(2026, 7, 4));
        FederalHolidayResponse response = FederalHolidayResponse.builder()
                .id(1L)
                .country(FederalCountry.USA)
                .name("Updated Holiday")
                .date(LocalDate.of(2026, 7, 4))
                .build();
        when(holidayService.updateHoliday(eq(1L), any())).thenReturn(response);
        mockMvc.perform(put("/api/holidays/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

    }

    @Test
    void shouldDeleteHoliday() throws Exception {
        doNothing().when(holidayService).deleteHoliday(1L);
        mockMvc.perform(delete("/api/holidays/1"))
                .andExpect(status().isOk());
        verify(holidayService).deleteHoliday(1L);
    }

    @Test
    void shouldUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "holidays.csv",
                "text/csv",
                """
                    country,name,date
                    USA,Independence Day,2026-07-04
                    """.getBytes());
        when(holidayService.uploadHolidays(any())).thenReturn(1);

        mockMvc.perform(multipart("/api/holidays/upload").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenHolidayNotFound() throws Exception {
        when(holidayService.getHolidaysByCountry(FederalCountry.USA))
                .thenThrow(
                        new HolidayNotFoundException("No holidays found")
                );
        mockMvc.perform(get("/api/holidays/country/USA"))
                .andExpect(status().isNotFound());

    }

}
