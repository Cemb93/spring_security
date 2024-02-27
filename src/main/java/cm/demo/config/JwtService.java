package cm.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  // https://randomkeygen.com/
  // ACA SE IMPLEMENTA TODO EL TEMA DE LA VALIDACION DEL TOKEN
  // private static final String SECRET_KEY = "t[XL(k[!)qS<Ms,l9(E^6j<[]*(FI9Uz=p~QQkvLkf}&`ie?3Gv7I&N(e9*qFtg";
  private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // SE CREA EL METODO
  // SE OBTIENE EL USUARIO DEL TOKEN
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // OBTENER UN SOLO CLAIM
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // ESTE METODO SE CONECTA CON LA CLASE "AuthenticacionService"
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  // SE GENERA EL TOKEN DE ACCESO
  public String generateToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails
  ) {
    return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  // SE VALIDA EL TOKEN DE ACCESO
//  public boolean isTokenValid(String token) {
//    try {
//      Jwts.parserBuilder()
//              .setSigningKey(getSigningKey())
//              .build()
//              .parseClaimsJws(token)
//              .getBody();
//      return true;
//    } catch (Exception e) {
//      System.out.println("Error al validar el token por: " + e.getMessage());
//      return false;
//    }
//  }
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // SE OBTIENEN TODOS LOS CLAIMS DEL TOKEN
  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSigningKey())
            .build().parseClaimsJws(token).getBody();
  }

  // SE OBTIENE LA FIRMA DEL TOKEN
  private Key getSigningKey() {
    // byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    // return Keys.hmacShaKeyFor(keyBytes);
    return SECRET_KEY;
  }
}
