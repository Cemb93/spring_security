package cm.demo.validation;

import cm.demo.auth.RegisterRequest;
import cm.demo.tools.Regex;
import cm.demo.validation.items.Email;
import cm.demo.validation.items.FullName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationRegister {

  @Autowired
  private Regex regex;
  @Autowired
  private FullName fullName;
  @Autowired
  private Email email;

  public void validation(RegisterRequest body) {
//    System.out.println("VALIDATION BODY: " + body);
    fullName.name(body.getFirstname(), regex.regexName);

    fullName.lastName(body.getLastname(), regex.regexLastName);

    email.email(body.getEmail(), regex.emailRegex);

    if (!body.getPassword().trim().matches(regex.regexPassword)) {
      throw new IllegalArgumentException("La contraseña debe contener entre 8 - 16 caracteres, mayúsculas, números y caracteres especiales.");
    }
  }
}
