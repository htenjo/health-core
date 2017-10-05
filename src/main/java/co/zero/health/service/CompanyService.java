package co.zero.health.service;

import co.zero.health.model.Company;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by hernan on 7/2/17.
 */
public interface CompanyService extends GenericCrud<Company> {
    /**
     * @return
     */
    List<Company> findAll();
}