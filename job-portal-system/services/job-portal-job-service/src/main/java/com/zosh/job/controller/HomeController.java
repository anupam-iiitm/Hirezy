package com.zosh.job.controller;

import com.zosh.job.domain.UserRole;
import com.zosh.job.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse HomeController() {
        return new ApiResponse(
                "Service for managing job postings, search, and filtering --- "
                        + UserRole.ROLE_EMPLOYER,
                true);
    }
}
