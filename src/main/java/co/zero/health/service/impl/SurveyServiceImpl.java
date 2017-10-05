package co.zero.health.service.impl;

import co.zero.health.json.SurveyJs;
import co.zero.health.model.Survey;
import co.zero.health.model.SurveyState;
import co.zero.health.model.SurveyStatistics;
import co.zero.health.persistence.SurveyRepository;
import co.zero.health.persistence.SurveyStatisticRepository;
import co.zero.health.service.SurveyService;
import co.zero.health.util.SurveyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by hernan on 7/2/17.
 */
@Service
public class SurveyServiceImpl implements SurveyService {
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyStatisticRepository statisticRepository;

    @Override
    public List<Survey> findAllByPatient(Long patientId) {
        return surveyRepository.findAllByPatientId(patientId);
    }

    @Override
    public Survey save(Survey entity) {
        return surveyRepository.save(entity);
    }

    @Override
    public Iterable<Survey> save(Iterable<Survey> entities) {
        return surveyRepository.save(entities);
    }

    @Transactional
    @Override
    public Survey update(Survey survey) {
        //First we validate the answers provided are valid json
        Map<String, Object> answers = SurveyUtils.parseSurveyAnswers(survey.getSurveyAnswers());
        SurveyJs surveyModel = SurveyUtils.parseSurveyModel(survey.getTemplate().getJsSurvey());

        //Then the survey should be updated
        Survey persistedSurvey = find((survey.getId()))
            .orElseThrow(() -> new IllegalArgumentException("Survey not found on update"));
        persistedSurvey.setSurveyAnswers(survey.getSurveyAnswers());
        persistedSurvey.setState(
                SurveyUtils.isSurveyFinished(surveyModel, answers) ? SurveyState.FINISHED : SurveyState.STARTED
        );


        //TODO: Then a statistic for the survey should be generated (Maybe this could be executed in async way)
        Long companyId = persistedSurvey.getPatient().getCompany().getId();
        Long eventId = Optional.ofNullable(persistedSurvey.getEvent())
                .map(event -> event.getId())
                .orElse(null);

        //Then the object with the answers ready to be query is persisted
        SurveyStatistics statistics = SurveyStatistics.builder()
                .surveyId(persistedSurvey.getId())
                .companyId(companyId)
                .eventId(eventId)
                .patientId(persistedSurvey.getPatient().getId())
                .specialtyId(persistedSurvey.getTemplate().getSpecialty().getId())
                .templateId(persistedSurvey.getTemplate().getId())
                .surveyAnswersCsv(SurveyUtils.formatAnswersAsCSV(answers, false))
                .build();
        statisticRepository.save(statistics);
        return save(persistedSurvey);
    }


    @Override
    public Optional<Survey> find(Long surveyId) {
        return Optional.ofNullable(surveyRepository.findOne(surveyId));
    }

    @Override
    public void delete(Long surveyId) {
        surveyRepository.delete(surveyId);
    }

    @Override
    @Transactional
    public void deleteAllByEventId(Long eventId) {
        statisticRepository.deleteAllByEventId(eventId);
        surveyRepository.deleteAllByEventId(eventId);
    }

    @Override
    @Transactional
    public void deleteAllByPatientId(Long patientId) {
        statisticRepository.deleteAllByPatientId(patientId);
        surveyRepository.deleteAllByPatientId(patientId);
    }
}
