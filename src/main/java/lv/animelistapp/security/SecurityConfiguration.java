package lv.animelistapp.security;

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    };

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    private static final String[] AUTH_WHITELIST = {
            "/", "/registration"
    };

    private static final String[] AUTH_SECURED = {
            "/secured/**", "/admin", "/admin/**"
    };

    private static final String[] AUTH_ADMIN = {
            "/admin1/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//.rememberMe().alwaysRemember(true)
                //.and()
                .requestCache().requestCache(new CustomRequestCache())

                .and()
                .authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                .requestMatchers(
                        VaadinWebSecurityConfigurerAdapter.getDefaultHttpSecurityPermitMatcher()
                ).permitAll()

                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(AUTH_SECURED).authenticated()
                .antMatchers(AUTH_ADMIN).hasAnyAuthority("ADMIN")

                .anyRequest().authenticated()

                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/VAADIN/**",
                        "/favicon.ico",
                        "/robots.txt",
                        "/manifest.webmanifest",
                        "/sw.js",
                        "/offline.html",
                        "/icons/**",
                        "/images/**",
                        "/styles/**",
                        "/h2-console/**");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
}
