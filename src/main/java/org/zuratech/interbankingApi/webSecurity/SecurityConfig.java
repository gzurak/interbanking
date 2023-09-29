package org.zuratech.interbankingApi.webSecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth.token}")
    private String authToken;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/ping").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password(authToken)
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    private class AuthenticationFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try{

                String token = request.getHeader("Authorization");
                if (token == null || !token.equals(authToken)) {
                    SecurityContextHolder.clearContext();
                }else{
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                filterChain.doFilter(request, response);
            }catch (Exception e){
                logger.error("Error obtaining token", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
            }
        }
    }

    @ControllerAdvice
    public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
        private final Logger log = LoggerFactory.getLogger(MyAuthenticationEntryPoint.class);
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                throws IOException, ServletException {
            // 401
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
        }

        @ExceptionHandler(value = {AccessDeniedException.class})
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AccessDeniedException accessDeniedException) throws IOException {
            // 403
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Failed : " + accessDeniedException.getMessage());
        }

        @ExceptionHandler (value = {Exception.class})
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             Exception exception) throws IOException {
            // 500
            log.info("Internal Server Error", exception);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error : " + exception.getMessage());
        }

    }

}