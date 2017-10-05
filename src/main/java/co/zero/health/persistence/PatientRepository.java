package co.zero.health.persistence;

import co.zero.health.model.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {
    List<Patient> findAllByCompanyId(Long companyId);

    /**
     * Find a {@link Patient} by his/her national identification
     * @param companyId company owner of the patient (Multiple companies can have the same patient).
     * @param nuip Patient identification
     * @return Patient if exist
     */
    Patient findOneByCompanyIdAndNuip(Long companyId, String nuip);
}