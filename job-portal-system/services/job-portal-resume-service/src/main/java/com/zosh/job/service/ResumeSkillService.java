package com.zosh.job.service;

import com.zosh.job.dto.ResumeSkillResponse;
import com.zosh.job.payload.AddResumeSkillRequest;

import java.util.List;

public interface ResumeSkillService {

    ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req) throws Exception;

    List<ResumeSkillResponse> getSkills(Long resumeId);

    ResumeSkillResponse updateSkill(
            Long skillId, Long resumeId, Long candidateId, AddResumeSkillRequest req
    ) throws Exception;
    void deleteSkill(Long skillId, Long resumeId, Long candidateId) throws Exception;
}
