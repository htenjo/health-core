package co.zero.health.service;

import co.zero.health.model.SurveyTemplate;

import java.util.List;

/**
 * Created by hernan on 7/2/17.
 */
public interface SurveyTemplateService extends GenericCrud<SurveyTemplate> {
    List<SurveyTemplate> findAllBySpecialtyId(Long specialtyId);
    SurveyTemplate save(SurveyTemplate surveyTemplate, Long specialtyId);
    SurveyTemplate update(SurveyTemplate surveyTemplate, Long specialtyId);
}