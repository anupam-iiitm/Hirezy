package com.zosh.job.mapper;

import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.dto.ApplicationNoteResponse;
import com.zosh.job.dto.ApplicationResponse;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.modal.Application;
import com.zosh.job.modal.ApplicationNote;
import com.zosh.job.modal.ApplicationScreening;
import com.zosh.job.payload.ApplicationScreeningResponse;
import com.zosh.job.payload.CreateApplicationRequest;

import java.util.List;

public class ApplicationMapper {

    public static Application toEntity(CreateApplicationRequest req,
                                       Long candidateId,
                                       Long companyId,
                                       Long employerId){
        if(req == null) return null;

        return Application.builder()
                .candidateId(candidateId)
                .jobId(req.getJobId())
                .companyId(companyId)
                .employerId(employerId)
                .resumeId(req.getResumeId())
                .coverLetter(req.getCoverLetter())
                .expectedSalary(req.getExpectedSalary())
                .availableFrom(req.getAvailableFrom())
                .status(ApplicationStatus.PENDING)

                .isStarred(false)
                .build();
    }

    public static ApplicationResponse toResponse(Application application,
                                                 List<ApplicationNote> notes,
                                                 JobResponse job,
                                                 CompanyResponse company,
                                                 UserResponse candidate,
                                                 ApplicationScreening screening
    ) {

        return ApplicationResponse.builder()
                .id(application.getId())
                .candidate(candidate)
                .employerId(application.getEmployerId())
                .job(job)
                .company(company)
                .status(application.getStatus())

                .resumeId(application.getResumeId())
                .coverLetter(application.getCoverLetter())

                .expectedSalary(application.getExpectedSalary())

                .availableFrom(application.getAvailableFrom())

                .isStarred(application.getIsStarred())
                .notes(notes.stream().map(ApplicationMapper::toNoteResponse).toList())
                .withdrawnAt(application.getWithdrawnAt())
                .withdrawnReason(application.getWithdrawnReason())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
                .screening(toScreeningResponse(screening))
                .build();
    }

    public static ApplicationScreeningResponse toScreeningResponse(
            ApplicationScreening s
    ){
        if(s == null) return null;

        return ApplicationScreeningResponse.builder()
                .id(s.getId())
                .overallScore(s.getOverallScore())
                .skillsMatchScore(s.getSkillsMatchScore())
                .experienceMatchScore(s.getExperienceMatchScore())
                .educationMatchScore(s.getEducationMatchScore())
                .shortlistStatus(s.getShortlistStatus())
                .summary(s.getSummary())
                .matchedSkills(s.getMatchedSkills())
                .missingSkills(s.getMissingSkills())
                .strengths(s.getStrengths())
                .concerns(s.getConcerns())
                .screenedAt(s.getScreenedAt())
                .build();
    }

    public static ApplicationNoteResponse toNoteResponse(ApplicationNote note) {
        return ApplicationNoteResponse.builder()
                .id(note.getId())
                .addedByUserId(note.getAddedByUserId())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .build();
    }
}
