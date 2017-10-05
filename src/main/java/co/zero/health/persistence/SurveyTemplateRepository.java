package co.zero.health.persistence;

import co.zero.health.model.SurveyTemplate;
import co.zero.health.model.SurveyType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyTemplateRepository extends PagingAndSortingRepository<SurveyTemplate, Long> {
    List<SurveyTemplate> findAllBySpecialtyId(Long specialtyId);

    @Query("SELECT t FROM SurveyTemplate t WHERE t.specialty.company.id = :companyId AND t.type = :type")
    List<SurveyTemplate> findAllByCompanyId(
            @Param("companyId") Long companyId,
            @Param("type")SurveyType type);
}