package cm.demo.auth;

import cm.demo.config.JwtService;
import cm.demo.models.EnumRole;
import cm.demo.models.RoleEntity;
import cm.demo.models.UserEntity;
import cm.demo.repository.UserRepository;
import cm.demo.validation.ValidationRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor // para poder inyectar
public class AuthenticationService {

  // SE REALIZA INYECCION
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final ValidationRegister validationRegister;

  public AuthenticationResponse register(RegisterRequest request) {
    //System.out.println("USUARIOS REGISTRADOS: " + repository.findAll());
    validationRegister.validation(request);
    //Role role = Role.valueOf(request.getRole()); // Convertir la cadena de rol a Enum Role

    // ACA SE PREPARA LA RELACION
    Set<RoleEntity> roles = request.getRoles().stream()
            .map(role -> RoleEntity.builder()
                    .name(EnumRole.valueOf(role))
                    .build()
            ).collect(Collectors.toSet());

    UserEntity user = UserEntity.builder()
            .firstname(request.getFirstname().trim())
            .lastname(request.getLastname().trim())
            .email(request.getEmail().trim())
            .password(passwordEncoder.encode(request.getPassword()).trim())
            .roles(roles) // ACA SE IMPLEMENTA LA RELACION
            .build();
    repository.save(user);

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .email(user.getEmail())
            .token(jwtToken)
            .roles(user.getRoles())
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    System.out.println("REQUEST AuthenticationResponse: " + request);

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail().trim(),
                    request.getPassword().trim()
            )
    );
    var user = repository.findByEmail(request.getEmail()).orElseThrow();
    System.out.println("USER: " + user);

    var jwtToken = jwtService.generateToken(user);
    System.out.println("-----------------------------------------------------------------");
    return AuthenticationResponse.builder().email(user.getEmail()).token(jwtToken).build();
  }
}
