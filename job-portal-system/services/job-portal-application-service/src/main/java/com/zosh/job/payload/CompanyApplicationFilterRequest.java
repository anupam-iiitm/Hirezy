package com.zosh.job.payload;

import com.zosh.job.domain.AiShortListStatus;
import com.zosh.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class CompanyApplicationFilterRequest {

    private Long jobId;

    private ApplicationStatus status;

    private Boolean isStarred=false;

    private AiShortListStatus aiShortListStatus;

    private Integer minAiScore;

    private String sortBy;
}
