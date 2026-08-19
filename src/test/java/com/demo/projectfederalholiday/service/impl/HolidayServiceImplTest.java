/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package src.test.java.com.demo.projectfederalholiday.service.impl;

import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayRequest;
import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayResponse;
import src.main.java.com.demo.projectfederalholiday.entity.FederalHoliday;
import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import src.main.java.com.demo.projectfederalholiday.exception.FileUploadException;
import src.main.java.com.demo.projectfederalholiday.exception.*;
import src.main.java.com.demo.projectfederalholiday.repository.FederalHolidayRepository;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.mock.web.MockMultipartFile;

/**
 *
 * @author Prabhakaran
 */
public class HolidayServiceImplTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayServiceImpl holidayService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddHolidaySuccessfully() {

        FederalHolidayRequest request = new FederalHolidayRequest(FederalCountry.USA, "Independence Day", LocalDate.of(2026, 7, 4));
        FederalHoliday holiday = FederalHoliday.builder()
                .id(1L)
                .country(FederalCountry.USA)
                .name("Independence Day")
                .date(LocalDate.of(2026, 7, 4))
                .build();
        when(holidayRepository.save(any(FederalHoliday.class))).thenReturn(holiday);
        FederalHolidayResponse response = holidayService.addHoliday(request);
        assertNotNull(response);
        assertEquals("Independence Day", response.getName());
        assertEquals(FederalCountry.USA, response.getCountry());
        verify(holidayRepository, times(1)).save(any(FederalHoliday.class));
    }

    @Test
    void shouldReturnAllHolidays() {
    	FederalHoliday holiday = FederalHoliday.builder()
                .id(1L)
                .country(FederalCountry.USA)
                .name("Christmas")
                .date(LocalDate.of(2026, 12, 25))
                .build();
        when(holidayRepository.findAll()).thenReturn(List.of(holiday));
        List<FederalHolidayResponse> response = holidayService.getAllHolidays();
        assertEquals(1, response.size());
        assertEquals("Christmas", response.get(0).getName());
        verify(holidayRepository).findAll();
    }

    @Test
    void shouldReturnHolidaysByCountry() {
    	FederalHoliday holiday = FederalHoliday.builder()
                .id(1L)
                .country(FederalCountry.CANADA)
                .name("Canada Day")
                .date(LocalDate.of(2026, 7, 1))
                .build();
        when(holidayRepository.findByCountry(FederalCountry.CANADA)).thenReturn(List.of(holiday));
        List<FederalHolidayResponse> response = holidayService.getHolidaysByCountry(FederalCountry.CANADA);
        assertEquals(1, response.size());
        assertEquals(FederalCountry.CANADA, response.get(0).getCountry());
    }

    @Test
    void shouldUpdateHolidaySuccessfully() {
        Long id = 1L;
        FederalHoliday existingHoliday = FederalHoliday.builder()
                .id(id)
                .country(FederalCountry.USA)
                .name("Old Name")
                .date(LocalDate.of(2026, 1, 1))
                .build();

        FederalHolidayRequest request = new FederalHolidayRequest(FederalCountry.USA, "New Name", LocalDate.of(2026, 12, 25));
        when(holidayRepository.findById(id)).thenReturn(Optional.of(existingHoliday));
        when(holidayRepository.save(any(FederalHoliday.class))).thenReturn(existingHoliday);
        FederalHolidayResponse response = holidayService.updateHoliday(id, request);
        assertEquals("New Name", response.getName());
        verify(holidayRepository).save(existingHoliday);
    }

    @Test
    void shouldThrowExceptionWhenHolidayNotFoundForUpdate() {
        when(holidayRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(
                HolidayNotFoundException.class,
                () -> holidayService.updateHoliday(100L,
                        new FederalHolidayRequest(FederalCountry.USA, "Test", LocalDate.now()))
        );
    }

    @Test
    void shouldDeleteHolidaySuccessfully() {
    	FederalHoliday holiday = FederalHoliday.builder()
                .id(1L)
                .country(FederalCountry.USA)
                .name("Christmas")
                .date(LocalDate.of(2026, 12, 25))
                .build();
        when(holidayRepository.findById(1L)).thenReturn(Optional.of(holiday));
        holidayService.deleteHoliday(1L);
        verify(holidayRepository).delete(holiday);
    }

    @Test
    void shouldThrowExceptionWhenHolidayNotFoundForDelete() {
        when(holidayRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(HolidayNotFoundException.class,
                () -> holidayService.deleteHoliday(1L)
        );
    }

    @Test
    void shouldUploadHolidaysSuccessfully() throws Exception {
        String csvContent = """
                country,name,date
                USA,Independence Day,2026-07-04
                CANADA,Canada Day,2026-07-01
                """;
        MockMultipartFile file  = new MockMultipartFile("file","holidays.csv","text/csv",csvContent.getBytes(StandardCharsets.UTF_8) );
        when(holidayRepository.saveAll(anyList())).thenReturn(List.of());
        int count = holidayService.uploadHolidays(file);
        assertEquals(2, count);
        verify(holidayRepository).saveAll(anyList());
    }

    @Test
    void shouldThrowExceptionForInvalidFile() {
        MockMultipartFile file = new MockMultipartFile("file","empty.csv","text/csv",new byte[0] );
        assertThrows(FileUploadException.class,
                () -> holidayService.uploadHolidays(file)
        );
    }
    
    @Test
    void shouldNotAddDuplicateHoliday() {
        FederalHolidayRequest request = new FederalHolidayRequest(FederalCountry.USA,"Independence Day",LocalDate.of(2026,7,4));
        when(holidayRepository.existsByCountryAndNameAndDate(FederalCountry.USA,"Independence Day",LocalDate.of(2026,7,4))).thenReturn(true);
        assertThrows(DuplicateHolidayException.class,() -> holidayService.addHoliday(request));
    }

}
