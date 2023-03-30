package mx.com.autofin.zuul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Clase de configuracion para peticiones preflight
 * omite paths con metodo options
 * el cual solo indica su disponibilidad
 * 
 * @author MNQ
 * @version 1.0
 *
 */

@Configuration
public class PreflightConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(final WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.OPTIONS);
	}
}
