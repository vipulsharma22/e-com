package com.nitsoft.ecommerce.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthController {

    @RequestMapping(value = APIName.HEALTH_API, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String health() {
        return "UP";
    }
}
