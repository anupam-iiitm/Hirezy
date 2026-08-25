package com.zosh.job.controller;

import com.zosh.job.modal.ApplicationScreening;
import com.zosh.job.service.ApplicationScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-screenings")
public class ScreeningController {

    private final ApplicationScreeningService applicationScreeningService;

    public ScreeningController(ApplicationScreeningService applicationScreeningService) {
        this.applicationScreeningService = applicationScreeningService;
    }

    @PostMapping
    public ResponseEntity<ApplicationScreening> createScreenings(
            @RequestParam Long applicationId,
            @RequestParam Long candidateId,
            @RequestParam Long jobId,
            @RequestParam Long resumeId
    ) {
        return ResponseEntity.ok(applicationScreeningService.screenAsync(
                applicationId,candidateId,jobId,resumeId
        ));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationScreening>> getScreenings() {
        return ResponseEntity.ok(applicationScreeningService.getAll());
    }
}
