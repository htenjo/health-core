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
@RequestMapping(value = "/health"
        , consumes = Constant.CONTENT_TYPE_JSON
        , produces = Constant.CONTENT_TYPE_JSON)
@SuppressWarnings(Constant.WARNING_UNUSED)
public class HealthController {
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getStatus() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
