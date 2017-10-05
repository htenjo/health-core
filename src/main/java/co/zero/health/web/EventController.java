package co.zero.health.web;

import co.zero.health.common.Constant;
import co.zero.health.model.Event;
import co.zero.health.model.Specialty;
import co.zero.health.service.CompanyService;
import co.zero.health.service.EventService;
import co.zero.health.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hernan on 7/2/17.
 */
@RestController
@RequestMapping(value = "/patient/{patientId}/event"
        , consumes = Constant.CONTENT_TYPE_JSON
        , produces = Constant.CONTENT_TYPE_JSON
)
@SuppressWarnings(Constant.WARNING_UNUSED)
public class EventController {
    private static final String PATIENT_ID_PARAM = "patientId";
    private static final String EVENT_ID_PARAM = "eventId";
    private static final String EVENT_ID_URL = "/{eventId}";
    @Autowired
    private EventService eventService;

    /**
     * @param patientId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Event>> findAll(@PathVariable(PATIENT_ID_PARAM) Long patientId) {
        List<Event> events = eventService.findAllByPatientId(patientId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * @param event
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Event> save(@PathVariable(PATIENT_ID_PARAM) Long patientId, @RequestBody Event event) {
        Event persistedEvent = eventService.save(patientId, event);
        return new ResponseEntity<>(persistedEvent, HttpStatus.CREATED);
    }

    /**
     * @param eventId
     * @return
     */
    @RequestMapping(value = EVENT_ID_URL, method = RequestMethod.GET)
    public ResponseEntity<Event> find(@PathVariable(EVENT_ID_PARAM) Long eventId) {
        return eventService.find(eventId)
                .map(event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * @param event
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Event> update(@PathVariable(PATIENT_ID_PARAM) Long patientId, @RequestBody Event event) {
        Event persistedEvent = eventService.update(patientId, event);
        return new ResponseEntity<>(persistedEvent, HttpStatus.CREATED);
    }

    /**
     * @param eventId
     * @return
     */
    @RequestMapping(value = EVENT_ID_URL, method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable(EVENT_ID_PARAM) Long eventId) {
        eventService.delete(eventId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}