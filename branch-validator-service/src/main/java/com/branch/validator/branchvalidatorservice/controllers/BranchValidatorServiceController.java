package com.branch.validator.branchvalidatorservice.controllers;

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
import com.branch.validator.branchvalidatorservice.model.Branch;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BranchValidatorServiceController {

    FactHandle fact1;
    String response;

    HashMap<String, String> map = new HashMap<>();

    @RequestMapping(value = "branches/{branchname}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)

    public Map<String, String> getBranches(@PathVariable String branchname) {
        System.out.println("Getting Branch details for " + branchname);

        try {
            KieServices ks = KieServices.Factory.get();
            KieFileSystem kFileSystem = ks.newKieFileSystem();
            kFileSystem.write(ResourceFactory.newClassPathResource("Rules.drl"));
            KieBuilder kBuilder = ks.newKieBuilder(kFileSystem);

            kBuilder.buildAll();
            KieModule kModule = kBuilder.getKieModule();

            KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());

            KieSession kSession = kContainer.newKieSession();

            Branch branch = new Branch();
            branch.setType(branchname);

            fact1 = kSession.insert(branch);
            kSession.fireAllRules();

            map.put("Branch Name", branch.getType());
            map.put("Discount Rate", branch.getDiscount());

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return map;
    }
}
