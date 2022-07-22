package com.company.integration;

import io.jmix.core.CoreConfiguration;
import io.jmix.core.annotation.JmixModule;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@ConfigurationPropertiesScan
@JmixModule(dependsOn = {CoreConfiguration.class})
@PropertySource(name = "com.company.integration", value = "classpath:/com/company/integration/module.properties")
public class IntConfiguration {
}
