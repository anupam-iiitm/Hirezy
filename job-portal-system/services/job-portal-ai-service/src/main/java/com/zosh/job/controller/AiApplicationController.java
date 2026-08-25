package com.zosh.job.controller;

import com.zosh.job.payload.*;
import com.zosh.job.service.ApplicationAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/application")
public class AiApplicationController {
    private final ApplicationAiService applicationAiService;


    @PostMapping("/cover-letter")
    public ResponseEntity<AiTextResponse> generateCoverLetter(
            @Valid @RequestBody CoverLetterRequest request
            ) throws Exception {
        AiTextResponse response=applicationAiService.generateCoverLetter(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/screening-core")
    public ResponseEntity<ScreeningScoreResponse> scoreCandidate(
            @Valid @RequestBody ScreeningScoreRequest request
    ) throws Exception {
        return ResponseEntity.ok(applicationAiService.scoreCandidate(request));
    }

    @PostMapping("/skills-gap")
    public ResponseEntity<SkillsGapResponse> analyzeSkillsGap(
            @Valid @RequestBody SkillsGapRequest request
    ) throws Exception {
        return ResponseEntity.ok(applicationAiService.analyzeSkillsGap(request));
    }
}
