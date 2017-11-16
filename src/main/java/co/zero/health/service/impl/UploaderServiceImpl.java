package co.zero.health.service.impl;

import co.zero.health.json.SurveyJs;
import co.zero.health.model.Event;
import co.zero.health.model.Patient;
import co.zero.health.model.Specialty;
import co.zero.health.model.Survey;
import co.zero.health.model.SurveyTemplate;
import co.zero.health.service.EventService;
import co.zero.health.service.PatientService;
import co.zero.health.service.SurveyService;
import co.zero.health.service.SurveyTemplateService;
import co.zero.health.service.UploaderService;
import co.zero.health.util.SurveyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service
public class UploaderServiceImpl implements UploaderService {
    private static final String NEW_LINE = "\n";
    private static final String COMMA = ",";
    private static final int PATIENT_ID_INDEX = 0;
    private static final int EVENT_ID_INDEX = 1;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private SurveyTemplateService templateService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private EventService eventService;


    @Override
    public int uploadInfo(Long templateId, String info) {
        SurveyTemplate template = this.findTemplate(templateId);

        for(String row : StringUtils.split(info, NEW_LINE)) {
            String[] columns = StringUtils.split(row, COMMA);
            String patientNuip = columns[PATIENT_ID_INDEX];
            String eventLoadedId = columns[EVENT_ID_INDEX];
            columns = Arrays.copyOfRange(columns, 2, columns.length);
            Set<String> questions = getQuestionsFromModel(template);
            validateQuestionsVsAnswers(questions, columns);

            Patient patient = findPatient(patientNuip);
            Event event = findEvent(patient.getId(), eventLoadedId, template);
            Optional<Survey> survey = surveyService.findByEventIdAndTemplateId(event.getId(), template.getId());

            String surveyAnswer = "";


        }

        return 0;
    }

    private SurveyTemplate findTemplate(Long templateId) {
        return templateService
                .find(templateId)
                .orElseThrow(() -> new IllegalArgumentException("::: Template not found"));
    }

    private Patient findPatient(String patientNuip) {
        return patientService.findByNuip(patientNuip)
                .orElseGet(() -> this.buildPatient(patientNuip));
    }

    private Event findEvent(Long patientId, String eventLoadedId, SurveyTemplate template) {
        return eventService.findByLoadedId(patientId, eventLoadedId)
                .orElseGet(() -> this.buildEvent(eventLoadedId, patientId, template.getSpecialty()));
    }

    private Patient buildPatient(String patientNuip) {
        Patient patient = new Patient();
        patient.setNuip(patientNuip);
        return patientService.save(patient);
    }

    private Event buildEvent(String eventLoadedId, Long patientId, Specialty specialty) {
        Event event = new Event();
        event.setCreatedDate(LocalDate.now());
        event.setName("Event #" + eventLoadedId);
        event.setSpecialty(specialty);
        event.setLoadedId(eventLoadedId);
        return eventService.save(patientId, event);
    }

    private Set<String> getQuestionsFromModel(SurveyTemplate template) {
        SurveyJs model = SurveyUtils.parseSurveyModel(template.getJsSurvey());
        return SurveyUtils.getQuestionNamesFromSurveyModel(model);
    }

    private void validateQuestionsVsAnswers(Set<String> questions, String[] columns) {
        if (questions.size() != columns.length) {
            throw new IllegalArgumentException("::: Uploaded info doesn't match the model");
        }
    }

}
