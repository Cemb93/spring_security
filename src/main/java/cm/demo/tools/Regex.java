package cm.demo.tools;

import org.springframework.stereotype.Component;

@Component
public class Regex {
  public String regexName = "^(?=\\p{L}{3,})([\\p{L}\\sáéíóúüñÁÉÍÓÚÜÑ\\-]+)$";
  public String regexLastName = "^(?=(?:.*\\p{L}{3,}\\s){2,})([\\p{L}\\s\\dáéíóúüñÁÉÍÓÚÜÑ\\-]+)$";
  public String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
  public String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{8,16}$";
}
