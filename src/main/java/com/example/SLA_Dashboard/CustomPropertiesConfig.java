package com.example.SLA_Dashboard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:SBConfig.properties")
public class CustomPropertiesConfig {

    private final Environment environment;

    @Value("${custom.property:default_value}")
    private String customProperty;

    public CustomPropertiesConfig(Environment environment) {
        this.environment = environment;
    }


}
