package mx.com.autofin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import mx.com.autofin.zuul.config.RestAccessDeniedHandler;

/**
 * Clase Spring Boot inicializa zuul gateway, punto de accesso http a apis de
 * AUTOFIN
 *
 * @author MNQ
 * @version 1.0
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableResourceServer
public class AutofinApplication extends ResourceServerConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AutofinApplication.class);

    private static final String MAIN_ROUTE = "autofin/v1/**";

    /**
     * Servicios privados
     */
    private static final String BITACORA_SERVICE =              "/autofin/v1/bitacora/**";
    private static final String MENU_REACT_SERVICE =            "/autofin/v1/sgcadmtmenu/**";
    private static final String ROLES_REACT_SERVICE =           "/autofin/v1/sgcadmaroles/**";
    private static final String PERMISOS_REACT_SERVICE =        "/autofin/v1/sgcadmapermisos/**";
    private static final String PERMISOS_ROLES_REACT_SERVICE =  "/autofin/v1/sgcadmapermisosroles/**";
    private static final String KEYCLOAK_ROLES_REACT_SERVICE =  "/autofin/v1/roleskeycloak/**";
    private static final String LDAP_USER_REACT_SERVICE =       "/autofin/v1/userldap/**";
    private static final String LDAP_SYNC_KEYCLOAK =            "/autofin/v1/ldap/synckeycloakldap/**";
    private static final String USERS_REACT_SERVICE =           "/autofin/v1/usuario/**";
    private static final String USERS_KEYCLOAK_SERVICE =        "/autofin/v1/userkeycloak/**";
    private static final String USERS_KEYCLOAK_USERDBSERVICE =  "/autofin/v1/userdbkeycloak/**";
    private static final String API_KEYCLOAK_SESIONESUSUARIO =  "/autofin/v1/keycloak/sesionesusuario/**";
    private static final String API_KEYCLOAK_SYNC =             "/autofin/v1/keycloak/synckeycloakldap/**";
    private static final String API_DB_FIRMA_DOCUMENTO =        "/autofin/v1/sgcfetdocumento/**";
    private static final String API_DB_FIRMA_C_TIPO_DOCUMENTO = "/autofin/v1/sgcfectipodocumento/**";
    private static final String API_DB_FIRMA_RECEPTOR =         "/autofin/v1/sgcfetreceptor/**";
    private static final String API_EFIRMA =                    "/autofin/v1/efirma/**";
    

    // Validacion de scope
    private static final String SCOPE_SGC = "#oauth2.hasScope('sgc')";

    /**
     * Servicios gestor-web
     */
    private static final String SGC_KEYCLOAK = "/autofin/v1/sgckeycloak/**";

    /**
     * Health check
     */
    private static final String ACTUATOR = "/actuator/**";

    /**
     * DECLARACION IMPORT DE CERTIFICADOS
     */
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.FALSE);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.DELETE);
        source.registerCorsConfiguration(MAIN_ROUTE, config);
        http.antMatcher(MAIN_ROUTE).cors().configurationSource(source)
                .and().antMatcher("/**").exceptionHandling().accessDeniedHandler(this.restAccessDeniedHandler).and()
                .authorizeRequests()
                .antMatchers(BITACORA_SERVICE).access(SCOPE_SGC)
                .antMatchers(MENU_REACT_SERVICE).access(SCOPE_SGC)
                .antMatchers(ROLES_REACT_SERVICE).access(SCOPE_SGC)
                .antMatchers(PERMISOS_REACT_SERVICE).access(SCOPE_SGC)
                .antMatchers(PERMISOS_ROLES_REACT_SERVICE).access(SCOPE_SGC)
                .antMatchers(KEYCLOAK_ROLES_REACT_SERVICE).access(SCOPE_SGC)
                .antMatchers(LDAP_USER_REACT_SERVICE).access(SCOPE_SGC)
                .antMatchers(USERS_REACT_SERVICE).access(SCOPE_SGC)
                .antMatchers(USERS_KEYCLOAK_SERVICE).access(SCOPE_SGC)
                .antMatchers(USERS_KEYCLOAK_USERDBSERVICE).access(SCOPE_SGC)
                .antMatchers(API_KEYCLOAK_SESIONESUSUARIO).access(SCOPE_SGC)
                .antMatchers(LDAP_SYNC_KEYCLOAK).access(SCOPE_SGC)                
                .antMatchers(API_KEYCLOAK_SYNC).access(SCOPE_SGC)
                .antMatchers(API_DB_FIRMA_DOCUMENTO).access(SCOPE_SGC)
                .antMatchers(API_DB_FIRMA_C_TIPO_DOCUMENTO).access(SCOPE_SGC)
                .antMatchers(API_DB_FIRMA_RECEPTOR).access(SCOPE_SGC)
                .antMatchers(API_EFIRMA).access(SCOPE_SGC)
                .antMatchers(SGC_KEYCLOAK).permitAll()
                .antMatchers(ACTUATOR).permitAll()
                .anyRequest().denyAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(null);
    }

    public static void main(String[] args) {

        SpringApplication.run(AutofinApplication.class);
    }

}
