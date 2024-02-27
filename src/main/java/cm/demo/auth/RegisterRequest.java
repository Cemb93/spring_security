package cm.demo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// ESTE SERIA EL DTO
public class RegisterRequest {

  //@Size(min = 7, max = 7, message = "El nombre debe tener 3 caracteres seguido de un espacio y otros 3 caracteres")
  //@Pattern(regexp = "[a-zA-Z]{3} [a-zA-Z]{3}", message = "Formato incorrecto para el nombre")
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private Set<String> roles;
}
