package com.zosh.job.client;

import com.zosh.job.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "JOB-PORTAL-USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/users/{userId}")
    UserResponse getUserById(
            @PathVariable Long userId);
}
