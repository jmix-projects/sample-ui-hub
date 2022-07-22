package com.company.hub.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.core.session.SessionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

@Component
public class MenuRetriever {

    private static final Logger log = LoggerFactory.getLogger(MenuRetriever.class);

    @Autowired
    private IntegrationProperties integrationProperties;

    @Autowired
    private ObjectProvider<SessionData> sessionDataProvider;

    public List<MenuItemDto> retrieveMenu(String appId) {
        String appUrl = integrationProperties.getAppUrls().get(appId) + "/integration/menu";

        String accessToken = (String) sessionDataProvider.getObject().getAttribute("accessToken");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(appUrl))
                .header("Authorization", "Bearer " + accessToken)
                .build();
        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() == HttpStatus.OK.value()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.body(),
                        mapper.getTypeFactory().constructCollectionType(List.class, MenuItemDto.class));
            } else {
                log.error("Error executing request: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error executing request", e);
        }
        return Collections.emptyList();
    }
}
