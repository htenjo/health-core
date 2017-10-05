package co.zero.health.persistence;

import co.zero.health.model.Specialty;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends PagingAndSortingRepository<Specialty, Long> {
    List<Specialty> findAllByCompanyId(Long companyId);
}
