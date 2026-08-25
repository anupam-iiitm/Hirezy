package com.zosh.job.service;

import com.zosh.job.client.CompanyClient;
import com.zosh.job.client.JobClient;
import com.zosh.job.client.ResumeClient;
import com.zosh.job.client.UserClient;
import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.dto.ApplicationResponse;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.dto.ResumeResponse;
import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.event.ApplicationEventPublisher;
import com.zosh.job.mapper.ApplicationMapper;
import com.zosh.job.modal.Application;
import com.zosh.job.modal.ApplicationNote;
import com.zosh.job.modal.ApplicationScreening;
import com.zosh.job.payload.CompanyApplicationFilterRequest;
import com.zosh.job.payload.CreateApplicationRequest;
import com.zosh.job.payload.WithdrawApplicationRequest;
import com.zosh.job.repository.ApplicationNoteRepository;
import com.zosh.job.repository.ApplicationRepository;
import com.zosh.job.repository.ApplicationScreeningRepository;
import com.zosh.job.repository.ApplicationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepository applicationRepository;
    private final ApplicationNoteRepository applicationNoteRepository;
    private final JobClient jobClient;
    private final ResumeClient resumeClient;
    private final CompanyClient companyClient;
    private final UserClient userClient;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationScreeningService applicationScreeningService;
    private final ApplicationScreeningRepository applicationScreeningRepository;


    @Override
    public ApplicationResponse createApplication(Long candidateId, CreateApplicationRequest req) throws Exception {

        if(applicationRepository.existsByCandidateIdAndJobId(candidateId,req.getJobId())){
            throw new Exception("you have already applied");
        }

        JobResponse job=jobClient.getJobById(req.getJobId());

        Long companyId=job.getCompany().getId();
        Long employeeId=job.getEmployerId();



        ResumeResponse resume=resumeClient.getResumeById(req.getResumeId(), candidateId);

        Application application= ApplicationMapper.toEntity(
                req, candidateId, companyId,employeeId
        );

        Application savedApplication= applicationRepository.save(application);

//      AI screening runs in a background thread, no callback needed
        applicationScreeningService.screenAsync(
                savedApplication.getId(),
                candidateId,
                req.getJobId(),
                req.getResumeId()
        );


        return buildFullResponse(savedApplication);
    }

    @Override
    public ApplicationResponse getApplicationById(Long id) throws Exception {
        Application application=getApplicationEntity(id);
        return buildFullResponse(application);
    }

    @Override
    public List<ApplicationResponse> getMyApplications(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId)
                .stream().map(
                        this::buildFullResponse
                ).toList();
    }

    @Override
    public List<ApplicationResponse> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream().map(
                        this::buildFullResponse
                ).toList();
    }

    @Override
    public List<ApplicationResponse> getApplicationsForCompany(Long userId,
                                                               CompanyApplicationFilterRequest filter) {
//      fetch company by ownerId
        Long companyId=companyClient.getMyCompany(userId).getId();
        Sort sort=buildSort(filter.getSortBy());


        return applicationRepository.findAll(
                ApplicationSpecification.forCompanyWithFilters(
                    companyId,
                    filter.getJobId(),
                    filter.getStatus(),
                    filter.getIsStarred(),
                    filter.getAiShortListStatus(),
                    filter.getMinAiScore()
                ),sort)
                .stream().map(
                        this::buildFullResponse
                ).toList();

    }



    @Override
    public ApplicationResponse updateStatus(Long applicationId,
                                            Long employerId,
                                            ApplicationStatus status) throws Exception {
        Application application=getApplicationEntity(applicationId);

        ApplicationStatus oldStatus=application.getStatus();
        assertEmployer(application,employerId);

        if(application.getStatus()==ApplicationStatus.WITHDRAWN){
            throw new Exception("candidate have already withdrawn");
        }
        application.setStatus(status);
        Application savedApplication= applicationRepository.save(application);


        applicationEventPublisher.publishStatusChange(application,
                oldStatus,status,
                "your application status get changed");


        return buildFullResponse(savedApplication);
    }


    @Override
    public ApplicationResponse withdraw(Long applicationId, Long candidateId,
                                        WithdrawApplicationRequest req) throws Exception {
        Application application=getApplicationEntity(applicationId);
        assertCandidate(application,candidateId);
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setWithdrawnReason(req.getReason());
        Application savedApplication= applicationRepository.save(application);
        return buildFullResponse(savedApplication);
    }



    @Override
    public ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception {
        Application application=getApplicationEntity(applicationId);
        assertEmployer(application,employerId);

        if(application.getIsStarred()==null){
            application.setIsStarred(true);
        }

        application.setIsStarred(!application.getIsStarred());
        Application savedApplication= applicationRepository.save(application);
        return buildFullResponse(savedApplication);
    }

    @Override
    public void deleteApplication(Long applicationId, Long candidateId) throws Exception {
        Application application=getApplicationEntity(applicationId);
        assertCandidate(application,candidateId);
        applicationRepository.delete(application);
    }

    @Override
    public Application getApplicationEntity(Long id) throws Exception {
        return applicationRepository.findById(id).orElseThrow(
                ()->new Exception("application not found")
        );
    }

    public ApplicationResponse buildFullResponse(Application application) {
// fetch real data from respective microservice

        JobResponse job=jobClient.getJobById(application.getJobId());
        CompanyResponse company=companyClient.getCompanyById(application.getCompanyId());
        UserResponse candidate=userClient.getUserById(application.getCandidateId());

        List<ApplicationNote> notes=applicationNoteRepository
                .findByApplicationId(application.getId());

        ApplicationScreening screening=applicationScreeningRepository
                .findByApplicationId(application.getId());

        return ApplicationMapper.toResponse(
                application,
                notes,
                job,
                company,
                candidate,
                screening
        );
    }

    private void assertCandidate(Application application, Long candidateId) throws Exception {
        if(!application.getCandidateId().equals(candidateId)){
            throw new Exception("you are not the owner of this application");
        }
    }

    private void assertEmployer(Application application, Long employerId) throws Exception {
        if(!application.getEmployerId().equals(employerId)) {
            throw new Exception("you are not the employer for this application");
        }
    }

    private Sort buildSort(String sortBy) {
        if("AI_SCORE_DESC".equals(sortBy)){
            return Sort.by(Sort.Order.desc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }
        else if("AI_SCORE_ASC".equals(sortBy)){
            return Sort.by(Sort.Order.asc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }
        return Sort.by(Sort.Direction.DESC, "appliedAt");
    }
}
