package com.zosh.job.service.impl;

import com.zosh.job.dto.LanguageResponse;
import com.zosh.job.mapper.ResumeMapper;
import com.zosh.job.modal.Language;
import com.zosh.job.modal.Resume;
import com.zosh.job.payload.AddLanguageRequest;
import com.zosh.job.repository.LanguageRepository;
import com.zosh.job.service.LanguageService;
import com.zosh.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final ResumeService resumeService;
    private final LanguageRepository languageRepository;

    @Override
    public LanguageResponse addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req) throws Exception {
        Resume resume=resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId);

        Language lang=Language.builder()
                .resume(resume)
                .languageName(req.getLanguageName())
                .proficiency(req.getProficiency())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();

        Language saved=languageRepository.save(lang);

        return ResumeMapper.toLanguageResponse(saved);
    }

    @Override
    public List<LanguageResponse> getLanguages(Long resumeId) {
        return languageRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toLanguageResponse).toList();
    }

    @Override
    public LanguageResponse updateLanguage(Long languageId, Long resumeId, Long candidateId, AddLanguageRequest req) throws Exception {
        Language lang=languageRepository.findById(languageId).orElseThrow(
                () -> new Exception("Language not found")
        );
        assertOwner(lang.getResume(),candidateId);
        lang.setLanguageName(req.getLanguageName());
        lang.setProficiency(req.getProficiency());
        if (req.getDisplayOrder() != null) lang.setDisplayOrder(req.getDisplayOrder());


        return ResumeMapper.toLanguageResponse(
                languageRepository.save(lang)
        );
    }

    @Override
    public void deleteLanguage(Long languageId, Long resumeId, Long candidateId) throws Exception {
        Language lang=languageRepository.findById(languageId).orElseThrow(
                () -> new Exception("Language not found")
        );
        assertOwner(lang.getResume(),candidateId);
        languageRepository.delete(lang);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if(!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("resume not found");
        }
    }
}
