package fr.univlorrainem1archi.friendsfiestas_v1.security;

import fr.univlorrainem1archi.friendsfiestas_v1.security.jwt.JwtAuthenticationEntryPoint;
import fr.univlorrainem1archi.friendsfiestas_v1.security.jwt.JwtAuthorizationFilter;
import fr.univlorrainem1archi.friendsfiestas_v1.security.jwt.SecurityConstant;
import fr.univlorrainem1archi.friendsfiestas_v1.security.jwt.handler.JwtAccessDeniedHandler;
import fr.univlorrainem1archi.friendsfiestas_v1.user.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthorizationFilter authorizationFilter;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    public SecurityConfig(JwtAuthorizationFilter authorizationFilter, JwtAuthenticationEntryPoint authenticationEntryPoint,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler,
                          @Qualifier("UserDetailsServiceImpl") UserDetailsServiceImpl userDetailsService){
        this.authorizationFilter = authorizationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(SecurityConstant.PUBLIC_URLS).permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
