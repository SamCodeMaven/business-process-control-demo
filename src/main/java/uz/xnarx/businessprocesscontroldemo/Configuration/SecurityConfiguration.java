package uz.xnarx.businessprocesscontroldemo.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import uz.xnarx.businessprocesscontroldemo.constants.ProjectEndpoints;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static uz.xnarx.businessprocesscontroldemo.Entity.Permission.*;
import static uz.xnarx.businessprocesscontroldemo.Entity.Role.ADMIN;
import static uz.xnarx.businessprocesscontroldemo.Entity.Role.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/auth/authenticate", "/api/auth/refresh-token",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(GET, ProjectEndpoints.USERS,ProjectEndpoints.USER_ID).hasAnyRole(ADMIN.name())
                                .requestMatchers(POST,ProjectEndpoints.USER_REGISTER).hasAnyRole(ADMIN.name())
                                .requestMatchers(PUT,ProjectEndpoints.USER_ENABLE,ProjectEndpoints.USER_DISABLE,
                                        ProjectEndpoints.BILL_APPROVE).hasAnyRole(ADMIN.name())
                                .requestMatchers(GET,ProjectEndpoints.USER_INFO,
                                        ProjectEndpoints.BILLINGS,
                                        ProjectEndpoints.PRODUCTS,ProjectEndpoints.PRODUCT_DETAILS,
                                        ProjectEndpoints.CLIENTS,ProjectEndpoints.CLIENT_NAME).hasAnyRole(ADMIN.name(),MANAGER.name())
                                .requestMatchers(POST,ProjectEndpoints.USER_TOKEN,ProjectEndpoints.USER_AUTH,
                                        ProjectEndpoints.BILLING,
                                        ProjectEndpoints.PRODUCT_SAVE,ProjectEndpoints.PRODUCT_RESTOCK,ProjectEndpoints.PRODUCT_SOLD,
                                        ProjectEndpoints.CLIENT_REGISTER).hasAnyRole(ADMIN.name(),MANAGER.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
