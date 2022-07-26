package com.company.hub.ui.component;

import io.jmix.ui.component.mainwindow.LogoutButton;
import io.jmix.ui.sys.registration.ComponentRegistration;
import io.jmix.ui.sys.registration.ComponentRegistrationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Replaces the standard LogoutButton component implementation.
 */
@Configuration
public class UiComponentConfiguration {

    @Bean
    public ComponentRegistration hubLogoutButton() {
        return ComponentRegistrationBuilder.create(LogoutButton.NAME)
                .withComponentClass(HubLogoutButtonImpl.class)
                .withComponentLoaderClass(HubLogoutButtonLoader.class)
                .build();
    }

}
