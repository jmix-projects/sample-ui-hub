package com.company.integration.logout;

import io.jmix.core.security.CurrentAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    private CurrentAuthentication currentAuthentication;

    private SessionRegistry sessionRegistry;

    public LogoutController(CurrentAuthentication currentAuthentication) {
        this.currentAuthentication = currentAuthentication;
    }

    @Autowired(required = false)
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/integration/logout")
    public void logout() {
        if (sessionRegistry == null) {
            log.warn("Cannot perform logout. No SessionRegistry bean found.");
            return;
        }

        UserDetails currentUser = currentAuthentication.getUser();
        sessionRegistry.getAllPrincipals().stream()
                .filter(principal -> {
                    if (principal instanceof UserDetails) {
                        return ((UserDetails) principal).getUsername().equals(currentUser.getUsername());
                    }
                    return false;
                })
                .flatMap(principal -> sessionRegistry.getAllSessions(principal, false).stream())
                .forEach(SessionInformation::expireNow);
    }
}
