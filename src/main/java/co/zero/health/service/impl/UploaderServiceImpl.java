package co.zero.health.service.impl;


import co.zero.health.model.Company;
import co.zero.health.model.Patient;
import co.zero.health.model.Survey;
import co.zero.health.model.SurveyTemplate;
import co.zero.health.service.PatientService;
import co.zero.health.service.SurveyService;
import co.zero.health.service.SurveyTemplateService;
import co.zero.health.service.UploaderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UploaderServiceImpl implements UploaderService {
    private static final String NEW_LINE = "\n";
    private static final String COMMA = ",";
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private SurveyTemplateService templateService;
    @Autowired
    private PatientService patientService;


    @Override
    public int uploadInfo(Long templateId, String info) {
        //1. Find the template to build the fields order
        SurveyTemplate template = this.findTemplate(templateId);
        //2. Transform each row from CSV to POJO
        for(String row : StringUtils.split(info, NEW_LINE)) {
            String[] columns = StringUtils.split(row, COMMA);
            String patientNuip = columns[0];
            Patient patient = findPatient(patientNuip, template.getSpecialty().getCompany()); //3. Create a Patient if doesn't exist
            Survey survey = new Survey();
        }


        //4. Build a Survey
        //5. Build a SurveyModelAnswer following the Template order


        return 0;
    }

    private SurveyTemplate findTemplate(Long templateId) {
        return templateService
                .find(templateId)
                .orElseThrow(() -> new IllegalArgumentException("::: Template not found"));
    }

    private Patient findPatient(String patientNuip, Company company) {
        return patientService.findByNuip(patientNuip)
                .orElseGet(() -> this.buildPatient(patientNuip, company));
    }

    private Patient buildPatient(String patientNuip, Company company) {
        Patient patient = new Patient();
        patient.setNuip(patientNuip);
        patient.setCompany(company);
        return patientService.save(patient);
    }



}
