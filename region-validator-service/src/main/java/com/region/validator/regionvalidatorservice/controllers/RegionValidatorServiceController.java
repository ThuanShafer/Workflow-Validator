package com.region.validator.regionvalidatorservice.controllers;

import com.region.validator.regionvalidatorservice.model.Region;
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
public class RegionValidatorServiceController {

    FactHandle fact1;
    String offer;

    HashMap<String, String> map = new HashMap<>();

    @RequestMapping(value = "regions/{regionname}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)

    public Map<String, String> getRegions(@PathVariable String regionname) {
        System.out.println("Getting Branch details for " + regionname);

        try {
            KieServices ks = KieServices.Factory.get();
            KieFileSystem kFileSystem = ks.newKieFileSystem();
            kFileSystem.write(ResourceFactory.newClassPathResource("Rules.drl"));
            KieBuilder kBuilder = ks.newKieBuilder(kFileSystem);

            kBuilder.buildAll();
            KieModule kModule = kBuilder.getKieModule();

            KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());

            KieSession kSession = kContainer.newKieSession();

            Region region = new Region();
            region.setType(regionname);

            fact1 = kSession.insert(region);
            kSession.fireAllRules();

            map.put("Region Name", region.getType());
            map.put("Discount Rate", region.getDiscount());

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return map;
    }
}
