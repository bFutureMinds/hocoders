package com.barclaycardus.hackathon.hocoders.service.config;

import com.barclaycardus.hackathon.hocoders.service.dto.CoreRdsDbConfig;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by abhishek on 11/06/16.
 */
public class ServiceConfigHolder {

    private ObjectMapper objectMapper;
    private String serviceConfigFile;
    private JsonNode configRoot;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setServiceConfigFile(String serviceConfigFile) {
        this.serviceConfigFile = serviceConfigFile;
    }

    public void init() throws IOException {
        loadCoreConfig();
    }

    public void loadCoreConfig() throws IOException {
        configRoot = objectMapper.readTree(new File(serviceConfigFile));
    }

    public String getMessageForService(String environment) {
        return configRoot.path("service").path("message").path(environment).getTextValue();
    }

    public CoreRdsDbConfig getCoreRdsConfig() {
        return new CoreRdsDbConfig(configRoot.path("service").path("aws").path("url").getTextValue(),
                                   configRoot.path("service").path("aws").path("driver").getTextValue(),
                                   configRoot.path("service").path("aws").path("username").getTextValue(),
                                   configRoot.path("service").path("aws").path("password").getTextValue());
    }

}
