package com.zosh.job.mapper;

import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.dto.JobSkillResponse;
import com.zosh.job.dto.JobTagResponse;
import com.zosh.job.modal.Job;
import com.zosh.job.modal.JobCategory;
import com.zosh.job.modal.JobTag;
import com.zosh.job.modal.embeddable.JobLocation;
import com.zosh.job.modal.embeddable.SalaryRange;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class JobMapper {

    public static JobResponse toResponse(Job job, CompanyResponse companyResponse) {

        JobLocation loc=job.getLocation();
        SalaryRange sal=job.getSalaryRange();

        Set<JobSkillResponse> skills=job.getSkills()==null?
                Collections.emptySet()
                :job.getSkills().stream().map(JobSkillMapper::toJobSkillResponse)
                .collect(Collectors.toSet());

        Set<JobTagResponse> tags=job.getTags()==null?
                Collections.emptySet(): job.getTags().stream().map(JobTagMapper::toTagResponse)
                        .collect(Collectors.toSet());



        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .employerId(job.getEmployerId())
                .company(companyResponse)
                .category(JobCategoryMapper.toJobCategoryResponse(job.getCategory(),false))
                .skills(skills)
                .tags(tags)
                // location
                .address(loc != null ? loc.getAddress() : null)
                .city(loc != null ? loc.getCity() : null)
                .state(loc != null ? loc.getState() : null)
                .country(loc != null ? loc.getCountry() : null)
                .zipCode(loc != null ? loc.getZipCode() : null)
                // salary
                .minSalary(sal != null ? sal.getMinSalary() : null)
                .maxSalary(sal != null ? sal.getMaxSalary() : null)

                // classification
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                // posting
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())

                // timestamps
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();
    }
}
