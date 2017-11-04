package co.zero.health.web;

import co.zero.health.common.Constant;
import co.zero.health.model.SurveyTemplate;
import co.zero.health.service.CompanyService;
import co.zero.health.service.SpecialtyService;
import co.zero.health.service.SurveyService;
import co.zero.health.service.SurveyTemplateService;
import co.zero.health.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hernan on 7/2/17.
 */
@RestController
@RequestMapping(value = "/specialty/{specialtyId}/surveyTemplate"
        , consumes = Constant.CONTENT_TYPE_JSON
        , produces = Constant.CONTENT_TYPE_JSON
)
@SuppressWarnings(Constant.WARNING_UNUSED)
public class SurveyTemplateController {
    private static final String SPECIALTY_ID_PARAM = "specialtyId";
    private static final String TEMPLATE_ID = "templateId";

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SurveyTemplateService surveyTemplateService;
    @Autowired
    private SurveyService surveyService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SurveyTemplate>> findAll(
            @PathVariable(SPECIALTY_ID_PARAM) Long specialtyId){
        Long companyId = SecurityUtil.getCompanyId();
        List<SurveyTemplate> surveys = surveyTemplateService.findAllBySpecialtyId(specialtyId);
        return new ResponseEntity<>(surveys, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SurveyTemplate> save(
            @PathVariable(SPECIALTY_ID_PARAM) Long specialtyId,
            @RequestBody SurveyTemplate surveyTemplate){
        SurveyTemplate persistedSurveyTemplate = surveyTemplateService.save(surveyTemplate, specialtyId);
        return new ResponseEntity<>(persistedSurveyTemplate, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<SurveyTemplate> update (
            @PathVariable(SPECIALTY_ID_PARAM) Long specialtyId,
            @RequestBody SurveyTemplate surveyTemplate){
        SurveyTemplate persistedSurveyTemplate = surveyTemplateService.update(surveyTemplate, specialtyId);
        return new ResponseEntity<>(persistedSurveyTemplate, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{surveyTemplateId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("surveyTemplateId") Long surveyTemplateId) {
        surveyTemplateService.delete(surveyTemplateId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{templateId}/statistics", method = RequestMethod.GET)
    public ResponseEntity<String> buildStatisticsByTemplate(@PathVariable(TEMPLATE_ID) Long templateId) {
        //TODO: Verify how to return the csv information
        StringBuilder csvInfo = new StringBuilder();
        surveyService.getStatistics(templateId)
                .forEach(surveyInfo -> {
                    csvInfo.append(surveyInfo);
                    csvInfo.append("\n");
                });
        return new ResponseEntity<>(csvInfo.toString(), HttpStatus.OK);
    }
}