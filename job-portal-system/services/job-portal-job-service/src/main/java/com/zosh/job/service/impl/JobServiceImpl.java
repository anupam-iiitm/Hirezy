package com.zosh.job.service.impl;

import com.zosh.job.client.CompanyClient;
import com.zosh.job.domain.JobStatus;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobRequest;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.mapper.JobMapper;
import com.zosh.job.modal.Job;
import com.zosh.job.modal.JobCategory;
import com.zosh.job.modal.JobSkill;
import com.zosh.job.modal.JobTag;
import com.zosh.job.modal.embeddable.JobLocation;
import com.zosh.job.modal.embeddable.SalaryRange;
import com.zosh.job.payload.JobSearchRequest;
import com.zosh.job.repository.JobRepository;
import com.zosh.job.repository.JobSpecification;
import com.zosh.job.service.JobCategoryService;
import com.zosh.job.service.JobService;
import com.zosh.job.service.JobSkillService;
import com.zosh.job.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobCategoryService categoryService;
    private final JobSkillService skillService;
    private final JobTagService tagService;
    private final CompanyClient companyClient;

    @Override
    public JobResponse createJob(Long employerId, JobRequest req) throws Exception {

        JobCategory category=categoryService.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> skills=req.getSkillIds()!=null?
                skillService.getSkillsByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTag> tags=req.getTagIds()!=null?
                tagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();


        CompanyResponse company=companyClient.getMyCompany(employerId);

        Long companyId=company.getId();

        Job job = Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requirements(req.getRequirements())
                .responsibilities(req.getResponsibilities())
                .benefits(req.getBenefits())
                .companyId(companyId)
                .employerId(employerId)
                .category(category)
                .skills(skills)
                .tags(tags)
                .location(buildLocation(req))
                .salaryRange(buildSalaryRange(req))
                .jobType(req.getJobType())
                .workMode(req.getWorkMode())
                .experienceLevel(req.getExperienceLevel())
                .openings(req.getOpenings() != null ? req.getOpenings() : 1)
                .applicationDeadline(req.getApplicationDeadline())
                .expiresAt(req.getExpiresAt())
                .active(true)
                .status(JobStatus.DRAFT)
                .build();

        Job savedJob=jobRepository.save(job);
        System.out.println("savedJob ================= "+savedJob);
        return convertToResponse(savedJob);
    }



    @Override
    public JobResponse getJobById(Long id) throws Exception {
        Job job=jobRepository.findById(id).orElseThrow(
                ()->new Exception("Job not found")
        );
        return convertToResponse(job);
    }

    @Override
    public List<JobResponse> getJobs(JobSearchRequest request) {
        List<Job> jobs=jobRepository.findAll(JobSpecification.build(request));
        return jobs.stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        List<Job> jobs=jobRepository.findByCompanyId(companyId);
        return jobs.stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) throws Exception {
        Job job=jobRepository.findById(jobId).orElseThrow(
                ()->new Exception("Job not found")
        );

        assertEmployer(job,employerId);

        JobCategory category=categoryService.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> skills=req.getSkillIds()!=null?
                skillService.getSkillsByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTag> tags=req.getTagIds()!=null?
                tagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();

        job.setTitle(req.getTitle());
        job.setDescription(req.getDescription());
        job.setRequirements(req.getRequirements());
        job.setResponsibilities(req.getResponsibilities());
        job.setBenefits(req.getBenefits());
        job.setCategory(category);
        job.setSkills(skills);
        job.setTags(tags);
        job.setLocation(buildLocation(req));
        job.setSalaryRange(buildSalaryRange(req));
        job.setJobType(req.getJobType());
        job.setWorkMode(req.getWorkMode());
        job.setExperienceLevel(req.getExperienceLevel());
        job.setOpenings(req.getOpenings() != null ? req.getOpenings() : job.getOpenings());
        job.setApplicationDeadline(req.getApplicationDeadline());
        job.setExpiresAt(req.getExpiresAt());
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) throws Exception {
        Job job=jobRepository.findById(jobId).orElseThrow(
                ()->new Exception("Job not found")
        );
        assertEmployer(job,employerId);
        if(job.getStatus()==JobStatus.CLOSED || job.getStatus()==JobStatus.EXPIRED){
            throw new Exception("Job is expired");
        }
        job.setStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now());
        job.setActive(true);
        return convertToResponse(jobRepository.save(job));
    }



    @Override
    public JobResponse closeJob(Long jobId, Long employerId) throws Exception {
        Job job=jobRepository.findById(jobId).orElseThrow(
                ()->new Exception("Job not found")
        );
        assertEmployer(job,employerId);

        job.setStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDateTime.now());
        job.setActive(false);
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) throws Exception {
        Job job=jobRepository.findById(jobId).orElseThrow(
                ()->new Exception("Job not found")
        );
        assertEmployer(job,employerId);
        jobRepository.delete(job);
    }

    @Override
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepository.findAll().stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }


    private JobResponse convertToResponse(Job savedJob) {

        CompanyResponse companyResponse=companyClient.getCompanyById(savedJob.getCompanyId());


        return JobMapper.toResponse(savedJob,companyResponse);

    }

    private SalaryRange buildSalaryRange(JobRequest req) {
        return SalaryRange.builder()
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();
    }

    private JobLocation buildLocation(JobRequest req) {
        return JobLocation.builder()
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .zipCode(req.getZipCode())
                .build();
    }

    private void assertEmployer(Job job, Long employerId) throws Exception {
        if(!job.getEmployerId().equals(employerId)) {
            throw new Exception("you are not the employer who posted this job");
        }
    }
}
