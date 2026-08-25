package com.zosh.job.payload;

import com.zosh.job.domain.AiShortListStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApplicationScreeningResponse {

    private Long id;
    private int overallScore;
    private int skillsMatchScore;
    private int experienceMatchScore;
    private int educationMatchScore;
    private AiShortListStatus shortlistStatus;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> strengths;
    private List<String> concerns;
    private String summary;
    private LocalDateTime screenedAt;
}
