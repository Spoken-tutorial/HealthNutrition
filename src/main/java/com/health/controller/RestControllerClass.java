package com.health.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.TopicCategoryMapping;
import com.health.model.Tutorial;
import com.health.service.TutorialService;
import com.health.utility.ServiceUtility;

@RestController
public class RestControllerClass {

    @Value("${scriptmanager_api}")
    private String scriptmanager_api;

    @Autowired
    private TutorialService tutService;

    private Map<Integer, String> latestPublishhedTutorial = new HashMap<>();

    public Map<Integer, String> getLatestPublishhedTutorial() {
        return latestPublishhedTutorial;
    }

    public void setLatestPublishhedTutorial(Map<Integer, String> latestPublishhedTutorial) {
        this.latestPublishhedTutorial = latestPublishhedTutorial;
    }

    @GetMapping("/tutorialsToUploadScripts")
    public Map<Integer, String> getPublishedTutorial() {
        Map<Integer, String> resultTutorial = new HashMap<>(latestPublishhedTutorial);
        Iterator<Map.Entry<Integer, String>> iterator = resultTutorial.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            int tutorialId = entry.getKey();
            Tutorial tutorial = tutService.findByTutorialId(tutorialId);
            if (tutorial != null) {
                ContributorAssignedTutorial conAssignedTutorial = tutorial.getConAssignedTutorial();
                TopicCategoryMapping topicCat = conAssignedTutorial.getTopicCatId();
                int catId = topicCat.getCat().getCategoryId();
                int lanId = conAssignedTutorial.getLan().getLanId();
                List<Integer> scriptVerList = ServiceUtility.getApiVersion(scriptmanager_api, catId,
                        tutorial.getTutorialId(), lanId);

                if (scriptVerList != null && scriptVerList.size() > 0) {

                    getLatestPublishhedTutorial().remove(tutorialId);
                }
            }
        }

        return latestPublishhedTutorial;
    }

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
