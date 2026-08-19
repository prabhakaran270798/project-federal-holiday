package src.main.java.com.demo.projectfederalholiday.controller;

import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayRequest;
import src.main.java.com.demo.projectfederalholiday.dto.FederalHolidayResponse;
import src.main.java.com.demo.projectfederalholiday.enums.FederalCountry;
import src.main.java.com.demo.projectfederalholiday.service.FederalHolidayService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/holidays")
@RequiredArgsConstructor
@Tag(
        name = "Swagger Project Federal Holiday",
        description = "This is to Add, Update and List Federal Holidays"
)
public class FederalHolidayController {

    private final HolidayService holidayService;

    // ---------- Read operations ----------
    
    @Operation(
            summary = "To List all federal holidays"
    )
    @GetMapping("/getAllHolidays")
    public ResponseEntity<List<FederalHolidayResponse>> getAllHolidays() {
        return ResponseEntity.ok(holidayService.getAllHolidays());
    }

    @Operation(
            summary = "To List holidays by country",
            description = "To List holidays for USA or CANADA"
    )
    @GetMapping("/getHolidays/country/{country}")
    public ResponseEntity<List<FederalHolidayResponse>> getHolidaysByCountry(@PathVariable Country country) {
        return ResponseEntity.ok(holidayService.getHolidaysByCountry(country));
    }
    
    // ---------- Write operations ----------

    @Operation(
            summary = "Add a federal holiday",
            description = "Creates a new federal holiday for USA or Canada"
    )
    @PostMapping("/addHolidays")
    public ResponseEntity<FederalHolidayResponse> addHoliday(@Valid @RequestBody FederalHolidayRequest request) {
        FederalHolidayResponse created = holidayService.addHoliday(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "To Update an existing holiday"
    )
    @PutMapping("/updateHolidays/{id}")
    public ResponseEntity<FederalHolidayResponse> updateHoliday(
            @PathVariable Long id,
            @Valid @RequestBody FederalHolidayRequest request) {
        return ResponseEntity.ok(holidayService.updateHoliday(id, request));
    }
    
    // ---------- Bulk operation ----------

    @Operation(
            summary = "To Upload holiday CSV file",
            description = "Uploads multiple federal holidays from a CSV file"
    )
    @PostMapping(
            value = "/uploadCSV/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadHolidayFile(@RequestParam("file") MultipartFile file) {
        int uploadCount = holidayService.uploadHolidays(file);
        return ResponseEntity.ok(uploadCount + " holidays uploaded successfully.");
    }
    
    // ---------- Delete operation ----------

    @Operation(
            summary = "To Delete a federal holiday",
            description = "Deletes a holiday using the provided holiday ID"
    )
    @DeleteMapping("/deleteHolidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }
}
