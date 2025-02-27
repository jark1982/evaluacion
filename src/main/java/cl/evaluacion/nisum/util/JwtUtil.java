package cl.evaluacion.nisum.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Inyecta la clave desde application.properties
    private String secret;
    
    @Value("${jwt.expiration}") // Inyecta la clave desde application.properties
    private Long expiration;
    // Método para convertir la clave String en un SecretKey
    private SecretKey getSecretKey() {
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    // Método para generar un token JWT
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 1 día
            .signWith(getSecretKey()) // Firma el token con la clave
            .compact();
    }

    // Método para validar un token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSecretKey()) // Usa la clave para verificar
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
