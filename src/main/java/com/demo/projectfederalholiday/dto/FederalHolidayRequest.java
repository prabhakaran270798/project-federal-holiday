/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.main.java.com.demo.projectfederalholiday.dto;

import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class FederalHolidayRequest {

    @NotNull(message = "Federal Country is required")
    private Country country;

    @NotBlank(message = "Federal Holiday name is required")
    private String name;

    @NotNull(message = "Federal Holiday date is required")
    private LocalDate date;
}
