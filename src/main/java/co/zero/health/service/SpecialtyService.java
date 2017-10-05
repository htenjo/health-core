package co.zero.health.service;

import co.zero.health.model.Specialty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hernan on 7/2/17.
 */
public interface SpecialtyService extends GenericCrud<Specialty>{
    List<Specialty> findAllByCompanyId(Long companyId);
}