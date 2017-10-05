package co.zero.health.service.impl;

import co.zero.health.model.Event;
import co.zero.health.model.Patient;
import co.zero.health.model.Survey;
import co.zero.health.model.SurveyType;
import co.zero.health.persistence.EventRepository;
import co.zero.health.persistence.SurveyStatisticRepository;
import co.zero.health.service.EventService;
import co.zero.health.service.PatientService;
import co.zero.health.service.SurveyService;
import co.zero.health.service.SurveyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by hernan on 7/2/17.
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SurveyTemplateService templateService;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private SurveyStatisticRepository statisticRepository;

    /**
     *
     * @param patientId
     * @param event
     * @return
     */
    @Transactional
    @Override
    public Event save(Long patientId, Event event) {
       Patient patient = findEventOwner(patientId);
       event.setPatient(patient);
       Event persistedEvent = eventRepository.save(event);

       List<Survey> surveys = templateService
               .findAllBySpecialtyId(event.getSpecialty().getId())
               .stream()
               .filter(template -> template.getType() != SurveyType.BASIC_INFO)
               .map(template -> new Survey(template, persistedEvent))
               .collect(Collectors.toList());
       surveyService.save(surveys);
       return persistedEvent;
    }

    /**
     *
     * @param patientId
     * @param event
     * @return
     */
    @Override
    public Event update(Long patientId, Event event) {
        Patient patient = findEventOwner(patientId);
        event.setPatient(patient);
        return eventRepository.save(event);
    }

    /**
     *
     * @param eventId
     * @return
     */
    @Override
    public Optional<Event> find(Long eventId) {
        return Optional.ofNullable(eventRepository.findOne(eventId));
    }

    /**
     *
     * @param eventId
     */
    @Override
    @Transactional
    public void delete(Long eventId) {
        surveyService.deleteAllByEventId(eventId);
        eventRepository.delete(eventId);
    }

    @Override
    public void deleteAllByPatientId(Long patientId) {
        surveyService.deleteAllByPatientId(patientId);
        eventRepository.deleteAllByPatientId(patientId);
    }

    /**
     *
     * @param patientId
     * @return
     */
    @Override
    public List<Event> findAllByPatientId(Long patientId) {
        return eventRepository.findAllByPatientId(patientId);
    }

    /**
     *
     * @param patientId
     * @return
     */
    private Patient findEventOwner(Long patientId) {
        return patientService
                .find(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient with doesn't exist"));
    }
}
