package com.company.hub.logout;

import com.company.hub.integration.IntegrationProperties;
import io.jmix.core.session.SessionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * Class performs a logout from all applications registered in the hub by invoking corresponding logout endpoints on
 * each application.
 */
@Component
public class HubLogout {

    private static final Logger log = LoggerFactory.getLogger(HubLogout.class);

    private IntegrationProperties integrationProperties;

    private ObjectProvider<SessionData> sessionDataProvider;

    public HubLogout(IntegrationProperties integrationProperties, ObjectProvider<SessionData> sessionDataProvider) {
        this.integrationProperties = integrationProperties;
        this.sessionDataProvider = sessionDataProvider;
    }

    public void logoutFromAllApps() {
        for (Map.Entry<String, String> entry : integrationProperties.getAppNames().entrySet()) {
            String appId = entry.getKey();
            String logoutUrl = integrationProperties.getAppUrls().get(appId) + "/integration/logout";
            String accessToken = (String) sessionDataProvider.getObject().getAttribute("accessToken");
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(logoutUrl))
                        .header("Authorization", "Bearer " + accessToken)
                        .build();

                HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

                if (response.statusCode() != HttpStatus.OK.value()) {
                    log.error("Error executing logout for {}: {}", appId, response.statusCode());
                }
            } catch (Exception e) {
                log.error("Error executing request", e);
            }
        }
    }
}
