package cm.demo.repository;

import cm.demo.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// SE EXTIENDE LA INTERFACE "UserRepository" DE LA INTEFACE "CrudRepository"
//public interface UserRepository extends CrudRepository<User, Integer> {
//  // SE CREA UN METODO
//  Optional<User> findByEmail(String email);
//}

// SE EXTIENDE LA INTERFACE "UserRepository" DE LA INTEFACE "JpaRepository"
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  // SE CREA UN METODO
  Optional<UserEntity> findByEmail(String email);
}
