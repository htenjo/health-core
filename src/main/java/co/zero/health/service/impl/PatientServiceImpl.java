package co.zero.health.service.impl;

import co.zero.health.model.Company;
import co.zero.health.model.Patient;
import co.zero.health.model.Survey;
import co.zero.health.model.SurveyType;
import co.zero.health.persistence.PatientRepository;
import co.zero.health.persistence.SurveyTemplateRepository;
import co.zero.health.service.EventService;
import co.zero.health.service.PatientService;
import co.zero.health.service.SurveyService;
import co.zero.health.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by hernan on 7/2/17.
 */
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private SurveyTemplateRepository templateRepository;
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private EventService eventService;

    @Transactional
    @Override
    public Patient save(Patient patient) {
        Company company = SecurityUtil.getCompany();
        patient.setCompany(company);
        final Patient persistedPatient = patientRepository.save(patient);
        //TODO: This could be done in async way - Just a future improve
        //All surveys configured for all Specialties with BASIC_INFO are created
        //All other surveys will be related when related events will be created
        List<Survey> basicSurveys = templateRepository
                .findAllByCompanyId(company.getId(), SurveyType.BASIC_INFO)
                .stream()
                .map(template -> new Survey(template, persistedPatient))
                .collect(Collectors.toList());
        surveyService.save(basicSurveys);
        return persistedPatient;
    }

    @Override
    public Patient update(Patient entity) {
        //Company company = SecurityUtil.getCompany();
        return patientRepository.save(entity);
    }

    @Override
    public Optional<Patient> find(Long patientId) {
        return Optional.ofNullable(patientRepository.findOne(patientId));
    }

    @Override
    @Transactional
    public void delete(Long patientId) {
        //TODO: Add security check agains the company
        eventService.deleteAllByPatientId(patientId);
        patientRepository.delete(patientId);

    }

    @Override
    public List<Patient> findAll() {
        Long companyId = SecurityUtil.getCompanyId();
        return patientRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Optional<Patient> findByNuip(String nuip) {
        Long companyId = SecurityUtil.getCompanyId();
        return Optional.ofNullable(patientRepository.findOneByCompanyIdAndNuip(companyId, nuip));
    }
}
