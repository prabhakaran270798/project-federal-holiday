/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main.java.com.demo.projectfederalholiday.service.impl;

import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayRequest;
import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayResponse;
import src.main.java.com.demo.projectfederalholiday.entity.FederalHoliday;
import src.main.java.com.demo.projectfederalholiday.exception.HolidayNotFoundException;
import src.main.java.com.demo.projectfederalholiday.repository.FederalHolidayRepository;
import src.main.java.com.demo.projectfederalholiday.service.FederalHolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import src.main.java.com.demo.projectfederalholiday.exception.FileUploadException;
import src.main.java.com.demo.projectfederalholiday.exception.DuplicateHolidayException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Prabhakaran
 */
@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;

    @Override
    public FederalHolidayResponse addHoliday(FederalHolidayRequest request) {
        if (holidayRepository.existsByCountryAndNameAndDate(request.getCountry(),request.getName(),request.getDate())) {
            throw new DuplicateHolidayException("Holiday already exists for the given country, name and date" );
        }
        FederalHoliday holiday = FederalHoliday.builder()
                .country(request.getCountry())
                .name(request.getName())
                .date(request.getDate())
                .build();
        FederalHoliday savedHoliday = holidayRepository.save(holiday);
        return mapToResponse(savedHoliday);
    }

    @Override
    public List<FederalHolidayResponse> getAllHolidays() {
        List<FederalHoliday> holidays = holidayRepository.findAll();
        return holidays.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<FederalHolidayResponse> getHolidaysByCountry(FederalCountry country) {
        List<FederalHoliday> holidays = holidayRepository.findByCountry(country);
        return holidays.stream().map(this::mapToResponse).toList();
    }

    @Override
    public FederalHolidayResponse updateHoliday(Long id, FederalHolidayRequest request) {
    	FederalHoliday holiday = holidayRepository.findById(id)
                .orElseThrow(()
                        -> new HolidayNotFoundException("Holiday not found with id: " + id)
                );
        boolean duplicateExists =holidayRepository.existsByCountryAndNameAndDateAndIdNot(request.getCountry(),request.getName(),request.getDate(), id);
        if (duplicateExists) {
            throw new DuplicateHolidayException("Holiday already exists for the given country, name and date");
        }
        holiday.setCountry(request.getCountry());
        holiday.setName(request.getName());
        holiday.setDate(request.getDate());
        Holiday updatedHoliday = holidayRepository.save(holiday);
        return mapToResponse(updatedHoliday);
    }

    @Override
    @Transactional
    public int uploadHolidays(MultipartFile file) {
        List<FederalHoliday> holidays = new ArrayList<>();
        if (file == null || file.isEmpty()) {
            throw new FileUploadException("Uploaded file is empty");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                FederalCountry country = FederalCountry.valueOf(data[0].trim().toUpperCase());
                String name = data[1].trim();
                LocalDate date = LocalDate.parse(data[2].trim());
                if (holidayRepository.existsByCountryAndNameAndDate(country,name,date)) {
                    throw new DuplicateHolidayException("Holiday already exists: " + name);
                }
                boolean duplicateInFile = holidays.stream()
                        .anyMatch(h ->
                                h.getCountry().equals(country)
                                && h.getName().equalsIgnoreCase(name)
                                && h.getDate().equals(date) );

                if (duplicateInFile) {
                	 throw new DuplicateHolidayException("Holiday already exists: " + name);
                }
                FederalHoliday holiday = Holiday.builder()
                        .country(FederalCountry.valueOf(data[0].trim().toUpperCase()))
                        .name(data[1].trim())
                        .date(LocalDate.parse(data[2].trim()))
                        .build();
                holidays.add(holiday);
            }
            
            holidayRepository.saveAll(holidays);
            return holidays.size();
        } catch (IOException e) {
            throw new FileUploadException("Failed to read file", e);
        }
    }

    @Override
    public void deleteHoliday(Long id) {
    	FederalHoliday holiday = holidayRepository.findById(id)
                .orElseThrow(()
                        -> new HolidayNotFoundException(
                        "Holiday not found with id: " + id
                ));
        holidayRepository.delete(holiday);
    }

    private FederalHolidayResponse mapToResponse(FederalHoliday holiday) {
        return FederalHolidayResponse.builder()
                .id(holiday.getId())
                .country(holiday.getCountry())
                .name(holiday.getName())
                .date(holiday.getDate())
                .build();
    }
}
