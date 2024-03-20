package cm.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<?> register(
          @RequestBody RegisterRequest request
  ) {
    try {
      AuthenticationResponse response = service.register(request);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      System.out.println("VALIDATION: " + e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    //return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
          @RequestBody AuthenticationRequest request
  ) {
    try {
      AuthenticationResponse response = service.authenticate(request);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      System.out.println("AUTHENTICATION ERROR: " + e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
//      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication failed: " + e.getMessage());
    }
//    return ResponseEntity.ok(service.authenticate(request));
  }
}
