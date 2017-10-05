package co.zero.health.persistence;

import co.zero.health.model.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    List<Company> findAll();
}
