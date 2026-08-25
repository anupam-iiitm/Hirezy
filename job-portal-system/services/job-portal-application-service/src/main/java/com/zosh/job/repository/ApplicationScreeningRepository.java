package com.zosh.job.repository;

import com.zosh.job.modal.ApplicationScreening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationScreeningRepository extends JpaRepository<
        ApplicationScreening,Long> {

    ApplicationScreening findByApplicationId(Long applicationId);


}
