package cm.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
// EXTENDEMOS DE LA CLASE "OncePerRequestFilter"
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//  @Autowired
  private final JwtService jwtService;
//  @Autowired
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    System.out.println("TOKEN: " + authHeader);
    final String jwt;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    // ACA SE EXTRAE EL TOKEN SIN EL TEXTO "Bearer "
    // "Bearer " = ESTA CADENA TIENE 7 CARACTERES
    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      // ESTA LINEA CONECTA CON LA CLASE "ApplicationConfig" Y LA FUNCION "userDetailsService"
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request,response);
  }
}


//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//  private final JwtService jwtService;
//
//  // SE GENERA EL CONSTRUCTOR
//  public JwtAuthenticationFilter(JwtService jwtService) {
//    this.jwtService = jwtService;
//  }
//
//  @Override
//  public Authentication attemptAuthentication(
//          HttpServletRequest request,
//          HttpServletResponse response
//  ) throws AuthenticationException {
//    // INFORMACION A RECUPERAR
//    UserEntity user = null;
//    String email = "";
//    String password = "";
//    try {
//      // SE MAPEA EL OBJETO
//      user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
//      email = user.getEmail();
//      password = user.getPassword();
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//            email, password
//    );
//    // getAuthenticationManager = ES EL OBJETO QUE SE ENCARGA DE ADMINISTRAR LA AUTENTICACION
//    return getAuthenticationManager().authenticate(authenticationToken);
//  }
//
//  @Override
//  protected void successfulAuthentication(
//          HttpServletRequest request,
//          HttpServletResponse response,
//          FilterChain chain,
//          Authentication authResult
//  ) throws IOException, ServletException {
//    User user = (User) authResult.getPrincipal();
//
//    String token = jwtService.generateToken(user);
//
//    response.addHeader("Authorization", token);
//
//    Map<String, Object> responseJson = new HashMap<>();
//    responseJson.put("usuario", user.getUsername());
//    responseJson.put("token", token);
//
//    response.getWriter().write(new ObjectMapper().writeValueAsString(responseJson));
//    response.getWriter().flush();
//    super.successfulAuthentication(request, response, chain, authResult);
//  }
//}
