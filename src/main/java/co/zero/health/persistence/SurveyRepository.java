package co.zero.health.persistence;

import co.zero.health.model.Survey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Repository
public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long> {
    @Query("SELECT s FROM Survey s WHERE s.patient.id = :patientId ORDER BY s.id ASC")
    List<Survey> findAllByPatientId(@Param("patientId") Long patientId);
    void deleteAllByEventId(Long eventId);
    void deleteAllByPatientId(Long patientId);

    /**
     * List all surveys filtering by Teamplte
     * @param templateId Identifier of the template filter
     * @return Stream of all surveys related with the given template
     */
    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
    Stream<Survey> findAllByTemplateId(Long templateId);

    Survey findByEventIdAndTemplateId(Long eventId, Long templateId);
}