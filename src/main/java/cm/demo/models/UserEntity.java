package cm.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
// SE IMPLEMENTA LA INTERFACE "UserDetails"
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  //@NotEmpty(message = "El nombre es obligatorio")
  private String firstname;
  private String lastname;
  private String email;
  private String password;

//  @Enumerated(EnumType.STRING)
//  private Role role;
  /*
  * targetEntity = modelo con el cual va hacer la relacion
  * FetchType.EAGER = trae todos los roles asociados al usuario
  * FetchType.LAZY = trae uno por uno los roles asociados al usuario
  * CascadeType.PERSIST = si se elimina un usuario de la DB, no se eliminan los roles relacionados
  * */
  @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
  /*
  * JoinTable = para la table intermedia
  * user_roles = nombre de la tabla intermedia
  * user_id = para la foreign key del usuario
  * role_id = para la foreign key de los roles
  * */
  @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  // Set = para evitar roles repetidos
  private Set<RoleEntity> roles;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(roles.toString()));
  }
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return List.of(new SimpleGrantedAuthority(role.name()));
//  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
