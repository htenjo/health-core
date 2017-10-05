package co.zero.health.web;

import co.zero.health.common.Constant;
import co.zero.health.model.Company;
import co.zero.health.service.CompanyService;
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
 * Created by hernan on 7/4/17.
 */
@RestController
@RequestMapping(value = "/company"
        , consumes = Constant.CONTENT_TYPE_JSON
        , produces = Constant.CONTENT_TYPE_JSON)
@SuppressWarnings(Constant.WARNING_UNUSED)
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Company>> listAll() {
        List<Company> companies = companyService.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Company> save(@RequestBody Company company) {
        Company persistedCompany = companyService.save(company);
        return new ResponseEntity<>(persistedCompany, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<Company> find(@PathVariable("companyId") Long companyId) {
        return companyService.find(companyId)
                .map(company -> new ResponseEntity<>(company, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Company> update(@RequestBody Company company) {
        if(company.getId() != null) {
            Company persistedCompany = companyService.update(company);
            return new ResponseEntity<>(persistedCompany, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{companyId}", method = RequestMethod.DELETE)
    public ResponseEntity<Company> delete(@PathVariable("companyId") Long companyId) {
        companyService.delete(companyId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
