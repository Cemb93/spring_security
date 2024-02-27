package cm.demo.validation.items;

import org.springframework.stereotype.Component;

@Component
public class FullName {

  public void name(String nombre, String regexName) {
    if (nombre == null || nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre es obligatorio");
    }
    if (!nombre.matches(regexName)) {
      throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
    }
  }

  public void lastName(String apellido, String regexLastName) {
    if (apellido == null || apellido.isEmpty()) {
      throw new IllegalArgumentException("El apellido es obligatorio");
    }
    if (!apellido.matches(regexLastName)) {
      throw new IllegalArgumentException("Debes ingresar al menos tus 2 primeros apellidos");
    }
  }

}
