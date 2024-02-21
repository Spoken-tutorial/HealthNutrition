package com.health.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.health.model.Tutorial;
import com.health.repository.TutorialRepository;
import com.health.service.TutorialService;

@RestController
public class RestControllerClass {
    private static final Logger logger = LoggerFactory.getLogger(RestControllerClass.class);
    @Autowired
    private TutorialService tutService;

    @Autowired
    private Environment env;
    @Autowired
    private TutorialRepository tutRepo;

    @GetMapping("/checkTutorial/{id}")
    public Map<String, Boolean> checkTutorial1(@PathVariable int id) {
        Tutorial tut = tutService.getById(id);
        Map<String, Boolean> result = new HashMap<>();
        if (tut == null) {
            result.put("published", null);
            return result;
        } else if (tut.isStatus()) {
            result.put("published", true);
            return result;
        } else {
            result.put("published", false);
            return result;
        }
    }

}
