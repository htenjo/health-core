package co.zero.health.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Class that configure the security required by the API with Auth0 provider.
 */
@Configuration
//TODO: Delete this when security configuration works
//@EnableWebSecurity(debug = true)
@PropertySources(@PropertySource("classpath:auth0.properties"))
public class HealthAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/company/**").hasAuthority("read:company")
                .antMatchers(HttpMethod.POST, "/company/*").hasAuthority("create:company")
                .antMatchers(HttpMethod.PUT, "/company/**").hasAuthority("update:company")
                .antMatchers(HttpMethod.DELETE, "/company/**").hasAuthority("delete:company")

                .antMatchers(HttpMethod.GET, "/specialty/*").hasAuthority("read:specialty")
                .antMatchers(HttpMethod.POST, "/specialty/*").hasAuthority("create:specialty")
                .antMatchers(HttpMethod.PUT, "/specialty/*").hasAuthority("update:specialty")
                .antMatchers(HttpMethod.DELETE, "/specialty/*").hasAuthority("delete:specialty")

                .antMatchers(HttpMethod.GET, "/patient/**").hasAuthority("read:patient")
                .antMatchers(HttpMethod.POST, "/patient/**").hasAuthority("create:patient")
                .antMatchers(HttpMethod.PUT, "/patient/**").hasAuthority("update:patient")
                .antMatchers(HttpMethod.DELETE, "/patient/**").hasAuthority("delete:patient")

                .antMatchers(HttpMethod.GET, "/specialty/*/surveyTemplate/**").hasAuthority("read:surveyTemplate")
                .antMatchers(HttpMethod.POST, "/specialty/*/surveyTemplate/**").hasAuthority("create:surveyTemplate")
                .antMatchers(HttpMethod.PUT, "/specialty/*/surveyTemplate/**").hasAuthority("update:surveyTemplate")
                .antMatchers(HttpMethod.DELETE, "/specialty/*/surveyTemplate/**").hasAuthority("delete:surveyTemplate")

                .antMatchers(HttpMethod.GET, "/patient/*/event/**").hasAuthority("read:event")
                .antMatchers(HttpMethod.POST, "/patient/*/event**").hasAuthority("create:event")
                .antMatchers(HttpMethod.PUT, "/patient/*/event**").hasAuthority("update:event")
                .antMatchers(HttpMethod.DELETE, "/patient/*/event**").hasAuthority("delete:event")

                //Any other resource just need to be authenticated
                .anyRequest().authenticated();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response
        // must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
