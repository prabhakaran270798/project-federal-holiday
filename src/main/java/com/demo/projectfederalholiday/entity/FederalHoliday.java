package src.main.java.com.demo.projectfederalholiday.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 *
 * @author Prabhakaran
 */
@Entity
@Table(name = "holidays")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FederalHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FederalCountry country;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;
}
