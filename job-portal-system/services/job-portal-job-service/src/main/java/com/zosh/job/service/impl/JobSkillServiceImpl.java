package com.zosh.job.service.impl;

import com.zosh.job.dto.JobSkillResponse;
import com.zosh.job.mapper.JobSkillMapper;
import com.zosh.job.modal.JobSkill;
import com.zosh.job.payload.JobSkillRequest;
import com.zosh.job.repository.JobSkillRepository;
import com.zosh.job.service.JobSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletRegistration;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {


    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createSkill(JobSkillRequest req) throws Exception {
        if(jobSkillRepository.existsByName(req.getName())){
            throw new Exception("skill name already exist");
        }
        String slug = generateUniqueSlug(req.getName());

        JobSkill skill = JobSkill.builder()
                .name(req.getName())
                .slug(slug)
                .category(req.getCategory())
                .active(true)
                .build();
        JobSkill savedSkill = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(savedSkill);
    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return jobSkillRepository.findByActiveTrue()
                .stream().map(JobSkillMapper::toJobSkillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobSkillResponse getSkillById(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(
                ()-> new Exception("job skill not found")
        );
        return JobSkillMapper.toJobSkillResponse(skill);
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest req) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(
                ()-> new Exception("job skill not found")
        );
        if(!skill.getName().equals(req.getName())
        && jobSkillRepository.existsByName(req.getName())){
            throw new Exception("skill name already exist");
        }

        skill.setName(req.getName());
        skill.setCategory(req.getCategory());
        JobSkill updated=jobSkillRepository.save(skill);

        return JobSkillMapper.toJobSkillResponse(updated);
    }

    @Override
    public void deleteSkill(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(
                ()-> new Exception("job skill not found")
        );
        skill.setActive(false);
        jobSkillRepository.save(skill);
    }

    @Override
    public Set<JobSkill> getSkillsByIds(Set<Long> ids) {
        Set<JobSkill> skills=new HashSet<>(jobSkillRepository.findAllById(ids));
        return skills;
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]","")
                .trim().replaceAll("[\\s-]+","-");
        if(!jobSkillRepository.existsBySlug(base)){
            return base;
        }

        int counter=1;
        while (jobSkillRepository.existsBySlug(base+"-"+counter)){
            counter++;
        }
        return base+"-"+counter;
    }
}
