package com.gdd.ylz.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Component
public class Property {
    @Value("${spring.profiles.active}")
    private String springProfilesActive;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("#{'${gdd.filter}'.empty ? null : '${gdd.filter}'.split(',')}")
    private List<String> filters;
    @Value("${swagger.version}")
    private String version;
    @Autowired
    private ConfigModules configModules;
    private List<String> list;

    public List<String> getPermissionsModules() {
        if (list == null) {
            list = new ArrayList<>();
            for (String key : configModules.getModules().keySet()) {
                if (filters.contains(key)) {
                    list.add(key);
                }
            }
        }
        return list;

    }

    public String getSpringProfilesActive() {
        return springProfilesActive;
    }

    public void setSpringProfilesActive(String springProfilesActive) {
        this.springProfilesActive = springProfilesActive;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ConfigModules getConfigModules() {
        return configModules;
    }

    public void setConfigModules(ConfigModules configModules) {
        this.configModules = configModules;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
