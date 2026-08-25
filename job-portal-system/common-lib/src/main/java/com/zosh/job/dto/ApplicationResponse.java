package com.zosh.job.dto;

import com.zosh.job.domain.AiShortListStatus;
import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.payload.ApplicationScreeningResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private Long id;
    private UserResponse candidate;
    private Long employerId;

    private JobResponse job;
    private CompanyResponse company;

    private ApplicationStatus status;

    // Submission content
    private Long resumeId;
    private String coverLetter;

    // Candidate preferences
    private BigDecimal expectedSalary;
    private LocalDate availableFrom;

    // Tracking
    private Boolean isStarred;


//  Todo
    private List<ApplicationNoteResponse> notes;

    // Withdrawal
    private LocalDateTime withdrawnAt;
    private String withdrawnReason;


    private Integer aiScore;

    private AiShortListStatus aishortListStatus;

    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    // AI screening result — null until background scoring completes
    private ApplicationScreeningResponse screening;
}
