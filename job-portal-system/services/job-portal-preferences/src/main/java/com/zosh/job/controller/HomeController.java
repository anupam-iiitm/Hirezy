package com.zosh.job.controller;

import com.zosh.job.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<?> Home(){
        String message="Service for managing candidate job preferences like saved jobs";
        ApiResponse res=new ApiResponse(message,true);
        return ResponseEntity.ok(res);
    }
}
