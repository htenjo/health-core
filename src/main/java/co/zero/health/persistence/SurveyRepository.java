package co.zero.health.persistence;

import co.zero.health.model.Survey;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long> {
    List<Survey> findAllByPatientId(Long patientId);
    void deleteAllByEventId(Long eventId);
    void deleteAllByPatientId(Long patientId);
}