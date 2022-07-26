package com.company.integration.oidc;

import io.jmix.core.JmixOrder;
import io.jmix.core.session.SessionProperties;
import io.jmix.oidc.userinfo.JmixOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A copy of {@link io.jmix.autoconfigure.oidc.OidcAutoConfiguration} that adds session management. This class should be
 * removed after <a
 * href="https://github.com/jmix-framework/jmix/issues/883">https://github.com/jmix-framework/jmix/issues/883</a> is
 * fixed.
 */
@EnableWebSecurity
@Order(JmixOrder.HIGHEST_PRECEDENCE + 100)
public class OAuth2LoginSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JmixOidcUserService jmixOidcUserService;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> {
                    authorizeRequests
                            //if we don't allow /vaadinServlet/PUSH URL the Session Expired toolbox won't
                            //be shown in the web browser
                            .antMatchers("/vaadinServlet/PUSH/**").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2Login(oauth2Login -> {
                    oauth2Login.userInfoEndpoint(userInfoEndpoint -> {
                        userInfoEndpoint.oidcUserService(jmixOidcUserService);
                    });
                })
                .logout(logout -> {
                    logout.logoutSuccessHandler(oidcLogoutSuccessHandler());
                })
                .csrf(csrf -> {
                    csrf.disable();
                });

        http.apply(new MySessionManagementConfigurer());
    }

    protected OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("{baseUrl}");
        return successHandler;
    }

    /**
     * The code is mostly copied from
     * {@link org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer} but it
     * registers its own SessionInformationExpiredStrategy that just continues a request.
     */
    public static class MySessionManagementConfigurer extends AbstractHttpConfigurer<MySessionManagementConfigurer, HttpSecurity> {

        @Override
        public void setBuilder(HttpSecurity http) {
            super.setBuilder(http);
            initSessionManagement(http);
        }

        private void initSessionManagement(HttpSecurity http) {
            try {
                ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);

                SessionAuthenticationStrategy sessionAuthenticationStrategy = applicationContext.getBean(SessionAuthenticationStrategy.class);
                SessionRegistry sessionRegistry = applicationContext.getBean(SessionRegistry.class);
                SessionProperties sessionProperties = applicationContext.getBean(SessionProperties.class);

                http.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy)
                        .maximumSessions(sessionProperties.getMaximumSessionsPerUser())
                        .expiredSessionStrategy(new DoNothingSessionExpirationStrategy())
                        .sessionRegistry(sessionRegistry);
            } catch (Exception e) {
                throw new RuntimeException("Error while init security", e);
            }
        }

        private static class DoNothingSessionExpirationStrategy implements SessionInformationExpiredStrategy {

            public DoNothingSessionExpirationStrategy() {
            }

            @Override
            public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
                HttpServletRequest request = event.getRequest();
                HttpServletResponse response = event.getResponse();
                response.sendRedirect(request.getRequestURI());
            }
        }
    }
}
