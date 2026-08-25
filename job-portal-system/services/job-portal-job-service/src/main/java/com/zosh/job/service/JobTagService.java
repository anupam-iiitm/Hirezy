package com.zosh.job.service;

import com.zosh.job.dto.JobTagResponse;
import com.zosh.job.modal.JobTag;
import com.zosh.job.payload.JobTagRequest;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createTag(JobTagRequest req) throws Exception;
    List<JobTagResponse> getAllTags();
    JobTagResponse getById(Long id) throws Exception;
    JobTagResponse updateTag(Long id, JobTagRequest req) throws Exception;
    void deleteTag(Long id) throws Exception;
    JobTag getTagEntityById(Long id) throws Exception;
    Set<JobTag> getTagsByIds(Set<Long> ids) throws Exception;

}
