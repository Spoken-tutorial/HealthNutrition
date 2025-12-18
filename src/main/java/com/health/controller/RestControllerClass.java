package com.health.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.health.model.ContributorAssignedTutorial;
import com.health.model.TopicCategoryMapping;
import com.health.model.Tutorial;
import com.health.service.TutorialService;
import com.health.threadpool.TaskProcessingService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;

@RestController
public class RestControllerClass {

    @Value("${git.commit.id}")
    private String gitCommitId;

    @Value("${git.commit.time:NA}")
    private String gitCommitTime;

    @Value("${scriptmanager_api}")
    private String scriptmanager_api;

    @Autowired
    private TutorialService tutService;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @GetMapping("/api/scriptPublished/{tutorialId}/{dateWithTime}")
    public ResponseEntity<Map<String, String>> isScriptPulished(@PathVariable int tutorialId,
            @PathVariable Timestamp dateWithTime) {
        Map<String, String> response = new HashMap<>();
        Tutorial tutorial = tutService.findByTutorialId(tutorialId);

        if (tutorial != null) {
            ContributorAssignedTutorial conAssignedTutorial = tutorial.getConAssignedTutorial();
            TopicCategoryMapping topicCat = conAssignedTutorial.getTopicCatId();
            int catId = topicCat.getCat().getCategoryId();
            int lanId = conAssignedTutorial.getLan().getLanId();
            List<Integer> scriptVerList = ServiceUtility.getApiVersion(scriptmanager_api, catId,
                    tutorial.getTutorialId(), lanId);

            if (scriptVerList != null && scriptVerList.size() > 0) {
                response.put("status", "okay");
                if (tutorial.isStatus()) {
                    if (tutorial.isAddedQueue()) {
                        taskProcessingService.addUpdateDeleteTutorial(tutorial, CommonData.UPDATE_DOCUMENT);
                    } else {
                        taskProcessingService.addUpdateDeleteTutorial(tutorial, CommonData.ADD_DOCUMENT);
                    }

                }

            } else {
                response.put("status", " not published");

            }
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "Tutorial not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        }
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

    @GetMapping("/check-deployment")
    public String checkDeployment() {
        return "Git Commit ID : " + gitCommitId + "<br>" + "Git Commit Time : " + gitCommitTime;

    }

}
