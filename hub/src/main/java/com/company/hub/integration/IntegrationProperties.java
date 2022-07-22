package com.company.hub.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "integration")
public class IntegrationProperties {

    private Map<String, String> appNames = new LinkedHashMap<>();
    private Map<String, String> appUrls = new LinkedHashMap<>();

    public Map<String, String> getAppNames() {
        return appNames;
    }

    public void setAppNames(Map<String, String> appNames) {
        this.appNames = appNames;
    }

    public Map<String, String> getAppUrls() {
        return appUrls;
    }

    public void setAppUrls(Map<String, String> appUrls) {
        this.appUrls = appUrls;
    }
}
