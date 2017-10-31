package co.zero.health.service.impl;

import co.zero.health.dto.SpecialtyStatisticsDTO;
import co.zero.health.model.Company;
import co.zero.health.model.Specialty;
import co.zero.health.persistence.SpecialtyRepository;
import co.zero.health.service.SpecialtyService;
import co.zero.health.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by hernan on 7/2/17.
 */
@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public Specialty save(Specialty specialty) {
        specialty.setCompany(SecurityUtil.getCompany());
        return specialtyRepository.save(specialty);
    }

    @Override
    public Optional<Specialty> find(Long specialtyId) {
        return Optional.ofNullable(specialtyRepository.findOne(specialtyId));
    }

    @Override
    public void delete(Long specialtyId) {
        specialtyRepository.delete(specialtyId);
    }

    @Override
    public List<Specialty> findAllByCompanyId(Long companyId) {
        return specialtyRepository.findAllByCompanyId(companyId);
    }

    @Override
    public List<SpecialtyStatisticsDTO> getStatistics(Long specialtyId) {
        return specialtyRepository.getStatistics(specialtyId)
                .stream()
                .map(row -> new SpecialtyStatisticsDTO(((BigInteger)row[0]).longValue(), (String)row[1]))
                .collect(Collectors.toList());
    }
}
