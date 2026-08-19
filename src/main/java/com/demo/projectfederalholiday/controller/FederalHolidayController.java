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
public class FederalHolidayController {

    private final HolidayService holidayService;

    // ---------- Read operations ----------
    
    @GetMapping("/getAllHolidays")
    public ResponseEntity<List<FederalHolidayResponse>> getAllHolidays() {
        return ResponseEntity.ok(holidayService.getAllHolidays());
    }

    @GetMapping("/getHolidays/country/{country}")
    public ResponseEntity<List<FederalHolidayResponse>> getHolidaysByCountry(@PathVariable Country country) {
        return ResponseEntity.ok(holidayService.getHolidaysByCountry(country));
    }
    
    // ---------- Write operations ----------

    @PostMapping("/addHolidays")
    public ResponseEntity<FederalHolidayResponse> addHoliday(@Valid @RequestBody FederalHolidayRequest request) {
        FederalHolidayResponse created = holidayService.addHoliday(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/updateHolidays/{id}")
    public ResponseEntity<FederalHolidayResponse> updateHoliday(
            @PathVariable Long id,
            @Valid @RequestBody FederalHolidayRequest request) {
        return ResponseEntity.ok(holidayService.updateHoliday(id, request));
    }
    
    // ---------- Bulk operation ----------

    @PostMapping(
            value = "/uploadCSV/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadHolidayFile(@RequestParam("file") MultipartFile file) {
        int count = holidayService.uploadHolidays(file);
        return ResponseEntity.ok(count + " holidays uploaded successfully.");
    }
    
 // ---------- Delete operation ----------

    @DeleteMapping("/deleteHolidays/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }
}
