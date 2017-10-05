package co.zero.health.persistence;

import co.zero.health.model.SurveyStatistics;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyStatisticRepository extends PagingAndSortingRepository<SurveyStatistics, Long> {
    void deleteAllByEventId(Long eventId);
    void deleteAllByPatientId(Long patientId);
}