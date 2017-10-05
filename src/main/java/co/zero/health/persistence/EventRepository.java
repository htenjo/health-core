package co.zero.health.persistence;

import co.zero.health.model.Event;
import co.zero.health.model.Specialty;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
    List<Event> findAllByPatientId(Long patientId);
    void deleteAllByPatientId(Long patientId);
}
