package com.company.integration;

import io.jmix.core.annotation.JmixModule;
import io.jmix.core.security.AuthorizedUrlsProvider;
import io.jmix.ui.UiConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@ComponentScan
@ConfigurationPropertiesScan
@JmixModule(dependsOn = {UiConfiguration.class})
@PropertySource(name = "com.company.integration", value = "classpath:/com/company/integration/module.properties")
public class IntConfiguration {

    @Bean
    public AuthorizedUrlsProvider myAuthorizedUrlsProvider() {
        return new AuthorizedUrlsProvider() {
            @Override
            public Collection<String> getAuthenticatedUrlPatterns() {
                return Arrays.asList("/integration/**");
            }

            @Override
            public Collection<String> getAnonymousUrlPatterns() {
                return Collections.emptyList();
            }
        };
    }
}
