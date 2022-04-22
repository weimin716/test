package com.gdd.ylz.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author Administrator
 */
@ConfigurationProperties(prefix = "gdd")
@Component
public class ConfigModules {
    private LinkedHashMap<String, String> modules;

    public LinkedHashMap<String, String> getModules() {
        return modules;
    }

    public void setModules(LinkedHashMap<String, String> modules) {
        this.modules = modules;
    }
}
