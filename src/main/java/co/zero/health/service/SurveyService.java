package co.zero.health.service;

import co.zero.health.model.Survey;

import java.util.List;
import java.util.Optional;

/**
 * Created by hernan on 7/2/17.
 */
public interface SurveyService extends GenericCrud<Survey> {
    List<Survey> findAllByPatient(Long patientId);

    Optional<Survey> findByEventIdAndTemplateId(Long eventId, Long templateId);
    Optional<Survey> findByPatientIdAndTemplateId(Long patientId, Long templateId);

    void deleteAllByEventId(Long eventId);

    void deleteAllByPatientId(Long patientId);

    /**
     * Given a SurveyTemplate, generate lines in format CSV from the Survey related
     *
     * @param templateId SurveyTemplate identifier
     * @return Lines in csv format from the survey responses.
     */
    String getStatistics(Long templateId);
}