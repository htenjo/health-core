package co.zero.health.service.impl;

import co.zero.health.model.Company;
import co.zero.health.persistence.CompanyRepository;
import co.zero.health.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by hernan on 7/4/17.
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void delete(Long companyId) {
        try {
            companyRepository.delete(companyId);
        }catch (EmptyResultDataAccessException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public Optional<Company> find(Long companyId) {
        return Optional.ofNullable(companyRepository.findOne(companyId));
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
