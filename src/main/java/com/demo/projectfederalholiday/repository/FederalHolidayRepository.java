/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package src.main.java.com.demo.projectfederalholiday.repository;

import src.main.java.com.demo.projectfederalholiday.entity.FederalHoliday;
import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

import java.util.List;

/**
 *
 * @author Prabhakaran
 */
@Repository
public interface FederalHolidayRepository extends JpaRepository<Holiday, Long> {

    List<Holiday> findByCountry(Country country);
    
    boolean existsByCountryAndNameAndDate(Country country, String name, LocalDate date);
    
    boolean existsByCountryAndNameAndDateAndIdNot(Country country, String name,LocalDate date, Long id );
}
