package com.company.integration;

import io.jmix.core.annotation.JmixModule;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(IntConfiguration.class)
@JmixModule(id = "com.company.integration.test", dependsOn = IntConfiguration.class)
public class IntTestConfiguration {
}
