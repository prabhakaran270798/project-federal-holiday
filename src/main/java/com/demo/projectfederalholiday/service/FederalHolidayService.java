/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package src.main.java.com.demo.projectfederalholiday.service;

import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayRequest;
import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayResponse;
import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Prabhakaran
 */
public interface FederalHolidayService {

    FederalHolidayResponse addHoliday(FederalHolidayRequest request);

    List<FederalHolidayResponse> getAllHolidays();

    List<FederalHolidayResponse> getHolidaysByCountry(FederalCountry country);

    FederalHolidayResponse updateHoliday(Long id, FederalHolidayRequest request);

    void deleteHoliday(Long id);
    
    int uploadHolidays(MultipartFile file);
}
