package cm.demo.validation.items;

import org.springframework.stereotype.Component;

@Component
public class Email {
  public void email(String correo, String emailRegex) {

    if (correo == null || correo.isEmpty()) {
      throw new IllegalArgumentException("El correo elecronico es obligatorio");
    }
    if (!correo.trim().matches(emailRegex)) {
      throw new IllegalArgumentException("Debes ingresar un correo valido");
    }
  }
}
