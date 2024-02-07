package com.springsecurity.practica.config;

import com.springsecurity.practica.Jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase que permite configurar el manejo de los filtros de Spring Security
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Este método permite configurar la cadena de filtros de seguridad, personalizando la seguridad de Spring Security
     * deshabilitando las sesiones, ya que se manejaran las autenticaciones y autorizaciones por medio de tokens,
     * se agrega el manejador de proveedor de autenticación, el cual está configurado en la clase ApplicationConfig,
     * donde se implementan los métodos que permiten realizar la autenticación y además se configuran las rutas de
     * los endpoints que serán públicos y privadas
     *
     * @param http
     * @return la cadena de filtros
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sessionConfig ->
                sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authRequestConfig -> {
                authRequestConfig.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                authRequestConfig.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();
                authRequestConfig.anyRequest().authenticated();
            })
            /*
            * con una expresión de método de referencia podemos llamar al método,
            * es equivalente a realizar la lambda, authRequestConfig -> httpRequestPath(authRequestConfig)
            */
            //.authorizeHttpRequests(this::httpRequestPath)
            .build();
    }

    /**
     * Este método permite configurar los accesos a las rutas de los endpoint con sus respectivos permisos
     * funciona de la misma manera que realizarlo en el mismo builder de http, con el método authorizeHttpRequests.
     * El fin de realizarlo en un método aparte es evitar la dificultad de lectura de configuración del securityFilterChain
     * al tener que colocar en el builder demasiadas rutas y permisos, de esta manera se hace más fácil verificar
     * que esté correcto.
     * @param authRequestConfig
     */
  /*  private void httpRequestPath(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRequestConfig) {
        authRequestConfig.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
        authRequestConfig.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();
        authRequestConfig.anyRequest().authenticated();
    }
   */
}
