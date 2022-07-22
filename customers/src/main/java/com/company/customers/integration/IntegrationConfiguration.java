package com.company.customers.integration;

import io.jmix.core.security.AuthorizedUrlsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class IntegrationConfiguration {

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
