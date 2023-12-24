package org.lawify.psp.mediator.shared.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.lawify.psp.mediator.identity.UserBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
@Service
public class JwtService implements IJwtService {
    @Value("${token.jwt.key}")
    private String secretKey;
    @Value("#{T(java.lang.Long).parseLong('${token.jwt.duration}')}")
    private long durationMs;
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserBase user) {
        HashMap<String, Object> claims = generateExtraClaims(user);
        claims.put("typ","Bearer");
        return generateBaseToken(claims,user, durationMs);
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) &&!isTokenExpired(token);
    }
    private String generateBaseToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails,
            long duration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();

    }

    private static HashMap<String, Object> generateExtraClaims(UserBase user) {
        var claims = new HashMap<String,Object>();
        claims.put("authorities", user.getAuthorities());
        claims.put("roles", user.getRoleNames());
        claims.put("sid", UUID.randomUUID().toString());
        claims.put("aud","account");
        claims.put("scope","email profile");
        claims.put("enabled", user.isEnabled());
        claims.put("preferred_username", user.getUsername());
        claims.put("given_name", user.getUsername());
        claims.put("id", user.getId());
        return claims;
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

}
