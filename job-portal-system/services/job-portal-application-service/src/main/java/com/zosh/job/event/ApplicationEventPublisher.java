package com.zosh.job.event;

import com.zosh.job.client.CompanyClient;
import com.zosh.job.client.JobClient;
import com.zosh.job.client.UserClient;
import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.modal.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApplicationEventPublisher {

    public static final String TOPIC = "application.status.changed";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserClient userClient;
    private final JobClient jobClient;
    private final CompanyClient companyClient;


    public void publishStatusChange(Application app,
                                    ApplicationStatus oldStatus,
                                    ApplicationStatus newStatus,
                                    String note) {


        try{
            UserResponse candidate=userClient.getUserById(app.getCandidateId());
            JobResponse job=jobClient.getJobById(app.getJobId());
            CompanyResponse company=companyClient.getCompanyById(app.getCompanyId());

            ApplicationStatusChangedEvent event=ApplicationStatusChangedEvent.builder()
                    .applicationId(app.getId())
                    .candidateId(app.getCandidateId())
                    .candidateEmail(candidate.getEmail())
                    .candidateName(candidate.getFullName())
                    .oldStatus(oldStatus)
                    .newStatus(app.getStatus())
                    .note(note)
                    .jobTitle(job.getTitle())
                    .companyName(company.getName())
                    .changedAt(LocalDateTime.now())
                    .build();

            kafkaTemplate.send(TOPIC, String.valueOf(app.getId()) , event);
        }catch (Exception e){
            System.out.println("Error in publishStatusChange ------------ "+e.getMessage());
        }



    }


}
