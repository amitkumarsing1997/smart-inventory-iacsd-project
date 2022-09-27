package com.iacsd.demo.util;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;


@Slf4j
public class TemplateEngineUtil {

    private static final Configuration configuration;

    static {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(TemplateEngineUtil.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    public static String buildTemplate(String templateName, Map<String, Object> templateData) {
        try {
            Template template = configuration.getTemplate(templateName);
            try(StringWriter out = new StringWriter()) {
                template.process(templateData, out);
                String mergedData = out.getBuffer().toString();
                out.flush();
                log.debug("Merged Data : {}", mergedData);
                return mergedData;
            }

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
