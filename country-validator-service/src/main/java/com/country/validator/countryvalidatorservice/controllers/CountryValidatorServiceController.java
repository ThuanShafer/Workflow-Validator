package com.country.validator.countryvalidatorservice.controllers;

import com.country.validator.countryvalidatorservice.model.Country;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.io.ResourceFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CountryValidatorServiceController {

    FactHandle fact1;
    String offer;

    HashMap<String, String> map = new HashMap<>();

    @RequestMapping(value = "countries/{countryname}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)

    public Map<String, String> getCountires(@PathVariable String countryname) {
        System.out.println("Getting Branch details for " + countryname);

        try {
            KieServices ks = KieServices.Factory.get();
            KieFileSystem kFileSystem = ks.newKieFileSystem();
            kFileSystem.write(ResourceFactory.newClassPathResource("Rules.drl"));
            KieBuilder kBuilder = ks.newKieBuilder(kFileSystem);

            kBuilder.buildAll();
            KieModule kModule = kBuilder.getKieModule();

            KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());

            KieSession kSession = kContainer.newKieSession();

            Country country = new Country();
            country.setType(countryname);

            fact1 = kSession.insert(country);
            kSession.fireAllRules();

            map.put("Country Name", country.getType());
            map.put("Discount Rate", country.getDiscount());

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return map;
    }
}
