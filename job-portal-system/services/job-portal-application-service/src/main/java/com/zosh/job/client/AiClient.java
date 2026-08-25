package com.zosh.job.client;

import com.zosh.job.dto.ApiResponse;
import com.zosh.job.payload.ScreeningScoreRequest;
import com.zosh.job.payload.ScreeningScoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//JOB-PORTAL-AI-SERVICE
@FeignClient("job-portal-ai-service")
public interface AiClient {

    @PostMapping("/api/ai/application/screening-core")
    ScreeningScoreResponse scoreCandidate(
            @RequestBody ScreeningScoreRequest request);
}
