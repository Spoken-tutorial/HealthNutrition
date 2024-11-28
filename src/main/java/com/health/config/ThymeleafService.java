package com.health.config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class ThymeleafService {

    private static final Logger logger = LoggerFactory.getLogger(ThymeleafService.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void createHtmlFromTemplate(String templateName, Map<String, Object> model, String outputFilePath,
            HttpServletRequest request, HttpServletResponse response) {
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariables(model);

        String htmlContent = templateEngine.process(templateName, context);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(htmlContent);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
    }
}
