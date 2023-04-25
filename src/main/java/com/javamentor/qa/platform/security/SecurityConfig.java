package com.javamentor.qa.platform.security;

import com.javamentor.qa.platform.security.jwt.JwtConfigurer;
import com.javamentor.qa.platform.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**",
                        "/error", "/webjars/**", "/login", "resources/static/**", "/swagger-resources/**", "/v2/api-docs/**"); //без последних 2 записей свагер ничего не видит
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.csrf().disable();
        http.cors().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                // всем остальным разрешаем доступ
                .antMatchers("/api/auth/token").permitAll()
                // ограничиваем доступ api/user/** - разрешен только USER
                .antMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
