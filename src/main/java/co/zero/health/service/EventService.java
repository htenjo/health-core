package co.zero.health.service;

import co.zero.health.model.Event;

import java.util.List;

/**
 * Created by hernan on 7/2/17.
 */
public interface EventService extends GenericCrud<Event>{
    List<Event> findAllByPatientId(Long patientId);
    Event save(Long patientId, Event event);
    Event update(Long patientId, Event event);
    void deleteAllByPatientId(Long patientId);
}