package io.mycompany.ppmtool.security;

import io.mycompany.ppmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static io.mycompany.ppmtool.security.SecurityConstants.H2_URLS;
import static io.mycompany.ppmtool.security.SecurityConstants.SIGN_UP_URLS;
import static io.mycompany.ppmtool.security.SecurityConstants.SIGN_UP_URL_HOME;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_IMAGES_ICO;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_IMAGES_PNG;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_IMAGES_SVG;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_IMAGES_GIF;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_IMAGES_JPG;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_DOC_TYPE_HTML;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_DOC_TYPE_CSS;
import static io.mycompany.ppmtool.security.SecurityConstants.URL_DOC_TYPE_JS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     *
     * @return
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .headers().frameOptions().sameOrigin() //Enable H2 db
            .and()
            .authorizeRequests()
            .antMatchers(SIGN_UP_URL_HOME,
                    URL_IMAGES_ICO,
                    URL_IMAGES_PNG,
                    URL_IMAGES_GIF,
                    URL_IMAGES_SVG,
                    URL_IMAGES_JPG,
                    URL_DOC_TYPE_HTML,
                    URL_DOC_TYPE_CSS,
                    URL_DOC_TYPE_JS).permitAll()
            .antMatchers(SIGN_UP_URLS).permitAll()
            .antMatchers(H2_URLS).permitAll()
            .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}