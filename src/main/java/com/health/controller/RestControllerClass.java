package com.health.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.health.model.Tutorial;
import com.health.service.TutorialService;

@RestController
public class RestControllerClass {
    @Autowired
    TutorialService tutService;

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
