package io.mycompany.ppmtool.security;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import io.mycompany.ppmtool.domain.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static io.mycompany.ppmtool.security.SecurityConstants.SECRET;
import static io.mycompany.ppmtool.security.SecurityConstants.EXPIRATION_TIME;

@Component
public class JwtTokenProvider {

    /**
     * This method has the logic to generate a JSON Web Token
     *
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullname", user.getFullname());

        return Jwts.builder()
                   .setSubject(userId)
                   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, SECRET)
                   .compact();
    }

    /**
     * This method will have the logic to validate the User's
     * token and throw the appropriate exceptions in any case
     * that the JWT is not valid.
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    // Get the user ID from token

    /**
     *
     * @param token
     * @return
     */
    public Long getUserIDFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }
}