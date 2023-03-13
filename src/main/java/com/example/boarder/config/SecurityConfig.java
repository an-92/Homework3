package com.example.boarder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig {

    //cors 설정 (외부 인가 서버를 사용하지 않는 방법)
    private final String[] CORS_ALLOW_METHOD = {"GET", "POST", "PATCH", "DELETE", "OPTIONS"};

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList(CORS_ALLOW_METHOD));
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    //기본적인 SecurityFilterChain 에 대한 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(); //HTTP 요청을 할 때 설정을 위한 것이라고 생각하면 편합니다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //REST API 는 기본적으로 Session 을 가지고 있지 않습니다. 따라서 상태를 유지하지 않는다는 설정입니다.

                .and()
                .authorizeRequests()
                .antMatchers("/login", "/permitAll/**").permitAll()
                //login url 에 대해서 모든 사람이 요청할 수 있다는 것을 명시

                .anyRequest()
                .authenticated()
                //나머지 요청에 대해서는 권한이 필요함을 명시

                .and()
                .csrf().disable();  //csrf 토큰은 포스트 요청시에 크로스사이트공격을 방지하기 위한 토큰으로써 jwt 토큰을 활용한 rest 요청시에는 필요하지 않습니다. jwt 토큰 자체가 식별자로 사용되기 때문입니다.
        return http.build();
    }

//    이 Bean 이 하는 일은 클라이언트가 서버로 요청할 때 security 를 거치지 않는 url 을 지정하는 부분 이라고 생각하면 됩니다.
//    git.ignore 와 같은 이치
    @Bean
    public WebSecurityCustomizer customWebSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/csrf", "/", "/swagger-resources/**", "/swagger-ui.html/**", "/v2/api-docs/**", "/webjars/**");
    }

//    패스워드를 암호화하는 기법을 설정,  Spring Security 에서 지정한 암호화 기법임으로 고정 값으로 봐도 무방
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
