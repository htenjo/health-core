package co.zero.health.service;

import co.zero.health.model.Survey;
import co.zero.health.model.SurveyTemplate;

import java.util.List;

/**
 * Created by hernan on 7/2/17.
 */
public interface SurveyService extends GenericCrud<Survey> {
    List<Survey> findAllByPatient(Long patientId);
    void deleteAllByEventId(Long eventId);
    void deleteAllByPatientId(Long patientId);
}