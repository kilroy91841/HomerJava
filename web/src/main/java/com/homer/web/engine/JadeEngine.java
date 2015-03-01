package com.homer.web.engine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import spark.ModelAndView;
import spark.TemplateEngine;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeException;
import de.neuland.jade4j.model.JadeModel;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.TemplateLoader;

/**
 * Created by arigolub on 3/1/15.
 */
public class JadeEngine extends TemplateEngine {

    private JadeConfiguration configuration;
    private String directory = new File(".").getCanonicalPath();

    public JadeEngine() throws IOException {
        this.configuration = new JadeConfiguration();
        //TODO use resource instead of bogus new File
        this.directory = this.directory + "/web/src/main/resources/templates/";
        TemplateLoader loader = new FileTemplateLoader(directory, "UTF-8");
        configuration.setTemplateLoader(loader);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String render(ModelAndView modelAndView) {
        StringWriter stringWriter = new StringWriter();
        try {
            JadeTemplate template = this.configuration.getTemplate(modelAndView.getViewName());
            JadeModel jadeModel = new JadeModel((Map<String, Object>) modelAndView.getModel());
            template.process(jadeModel, stringWriter);
        } catch (JadeException | IOException e) {
            e.getCause();
        }
        return stringWriter.toString();
    }
}
