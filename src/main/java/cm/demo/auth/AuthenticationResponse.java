package cm.demo.auth;

import cm.demo.models.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private String firstname;
  private String lastname;
  private String email;
  private String token;
  private Set<RoleEntity> roles;
}
