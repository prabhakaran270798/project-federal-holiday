/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main.java.com.demo.projectfederalholiday.dto;

import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import java.time.LocalDate;
import lombok.*;

/**
 *
 * @author Prabhakaran
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FederalHolidayResponse {

    private Long id;

    private Country country;

    private String name;

    private LocalDate date;
}
