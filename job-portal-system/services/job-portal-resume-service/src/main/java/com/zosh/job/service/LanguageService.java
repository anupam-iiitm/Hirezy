package com.zosh.job.service;

import com.zosh.job.dto.LanguageResponse;
import com.zosh.job.payload.AddLanguageRequest;

import java.util.List;

public interface LanguageService {

    LanguageResponse addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req) throws Exception;
    List<LanguageResponse> getLanguages(Long resumeId);
    LanguageResponse updateLanguage(
            Long languageId, Long resumeId, Long candidateId, AddLanguageRequest req
    ) throws Exception;

    void deleteLanguage(Long languageId, Long resumeId, Long candidateId) throws Exception;

}
