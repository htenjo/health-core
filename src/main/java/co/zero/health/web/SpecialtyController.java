package co.zero.health.web;

import co.zero.health.common.Constant;
import co.zero.health.model.Company;
import co.zero.health.model.Specialty;
import co.zero.health.service.CompanyService;
import co.zero.health.service.SpecialtyService;
import co.zero.health.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by hernan on 7/2/17.
 */
@RestController
@RequestMapping(value = "/specialty"
        , consumes = Constant.CONTENT_TYPE_JSON
        , produces = Constant.CONTENT_TYPE_JSON
)
@SuppressWarnings(Constant.WARNING_UNUSED)
public class SpecialtyController {
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Specialty>> findAll(){
        Long companyId = SecurityUtil.getCompanyId();
        List<Specialty> specialties = specialtyService.findAllByCompanyId(companyId);
        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Specialty> save(@RequestBody Specialty specialty){
        Specialty persistedSpecialty = specialtyService.save(specialty);
        return new ResponseEntity<>(persistedSpecialty, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{specialtyId}", method = RequestMethod.DELETE)
    public ResponseEntity<Specialty> delete(@PathVariable("specialtyId") Long specialtyId) {
        specialtyService.delete(specialtyId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}