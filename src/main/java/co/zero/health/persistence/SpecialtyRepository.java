package co.zero.health.persistence;

import co.zero.health.dto.SpecialtyStatisticsDTO;
import co.zero.health.model.Specialty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends PagingAndSortingRepository<Specialty, Long> {
    String STATISTIC_QUERY =
            " SELECT patient_id, string_agg(info, ', ') " +
            " FROM (" +
            "    SELECT patient_id, trim(survey_answers_csv) as info, finished" +
            "    FROM public.survey_statistics" +
            "    WHERE specialty_id = :specialtyId" +
            "    ORDER BY patient_id, template_id, survey_id" +
            "    ) as info" +
            " GROUP BY patient_id" +
            " HAVING bool_and(finished)" +
            " ORDER BY patient_id ASC";

    List<Specialty> findAllByCompanyId(Long companyId);

    @Query(value = STATISTIC_QUERY, nativeQuery = true)
    List<Object[]> getStatistics(@Param("specialtyId") Long specialtyId);
}
