package cm.demo.config;

import cm.demo.models.EnumRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

//  @Autowired
  private final JwtAuthenticationFilter jwtAuthFilter;
//  @Autowired
  private final AuthenticationProvider authenticationProvider;

  private static final String[] WHITE_LIST_URL = {
          "/api/v1/auth/**",
          "/medicos",
          "/medicos/**",
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    /*
    * ACA LAS RUTAS PERMITIDAS ESTAN EN LA CLASE "AuthenticationController"
    * CON "requestMatchers" SE DA PERMISO A ALGUNAS RUTAS ESPECIFICAS
    * COMO POR EJEMPLO:
    * "/api/v1/auth/**" Y LAS DEMAS RUTAS EN SU RELACION
    * csrf = Cross-Site Request Forgery, es una vulnerabilidad
    * SessionCreationPolicy.ALWAYS = CREA UNA SESION SIEMPRE Y CUANDO NO EXISTA NINGUNA, SI YA HAY UNA SESION EXISTENTE LA REUTILIZA
    * SessionCreationPolicy.IF_REQUIRED = CREA UNA NUEVA SESION SOLO SI ES NECESARIO, ES DECIR, SI LA SESSION NO EXISTE LA CREA O LA REUTILIZA
    * SessionCreationPolicy.NEVER = NO CREA NINGUNA SESION, PERO SI YA EXISTE UNA SESION LA UTILIZA
    * SessionCreationPolicy.STATELESS = NO CREA NINGUNA SESSION
    * */
//    http.csrf().disable().authorizeHttpRequests().requestMatchers("/api/v1/auth/**")
//            .permitAll().anyRequest().authenticated().and().sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and().authenticationProvider(authenticationProvider)
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    System.out.println("ROLE - ADMIN: " + EnumRole.ADMIN.toString() );
    System.out.println("ROLE - ADMIN - 2: " + EnumRole.ADMIN );

    http.csrf(config -> config.disable()).authorizeHttpRequests(auth -> {
      auth.requestMatchers(WHITE_LIST_URL).permitAll()
              .requestMatchers("/api/v1/auth/authenticate").hasAnyRole(EnumRole.ADMIN.toString());
      auth.anyRequest().authenticated();
    }).sessionManagement(session -> {
              session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                      .and().authenticationProvider(authenticationProvider)
                      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    });

    return http.build();
  }
}
