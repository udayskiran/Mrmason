package com.application.mrmason.template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class EmailTemplate {

    private String template;

    public EmailTemplate(String customTemplate) {
        try {
            this.template = loadTemplate(customTemplate);
        } catch (Exception e) {
            this.template = "Empty";
        }

    }

    private String loadTemplate(String customTemplate) throws IOException {
        return getResourceFileAsString(customTemplate);

    }

    public String getTemplate(Map<String, String> replacements) {

        String customTemplate = this.template;
        //Replace the String
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            customTemplate = customTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return customTemplate;
    }

    public static String getResourceFileAsString(String fileName) throws IOException {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new IOException("Resource not found");
        }
    }

    public static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = EmailTemplate.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

}