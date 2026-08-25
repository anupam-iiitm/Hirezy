package com.zosh.job.modal;

import com.zosh.job.domain.AiShortListStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationScreening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long applicationId;

    @Column(nullable = false)
    private Integer overallScore;

    private Integer skillsMatchScore;

    private Integer experienceMatchScore;

    private Integer educationMatchScore;

    private AiShortListStatus shortlistStatus;

    @Column(columnDefinition = "TEXT")
    private String summary;

//    job => spring boot, java, postgres, react, github, aws, docker
//    cskill=> node, spring boot, java, mysql, react, aws

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> matchedSkills;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> missingSkills;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> concerns;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> strengths;

    @CreationTimestamp
    private LocalDateTime screenedAt;

    private String screeningVersion="v1";
}
