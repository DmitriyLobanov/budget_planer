package com.lobanov.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lobanov.exсeptions.JwtAuthenticationException;
import com.lobanov.models.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private Long tokenValidTimeMilliseconds;

    @Value("${jwt.token.header}")
    private String tokenHeader;

    private JWTVerifier jwtVerifier;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    //не понял зочем...
    protected void init() {
        //secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username, List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //claims is custom map
        Map<String, List<String>> claims = new HashMap<>();
        claims.put("roles", roles);
        //List<String> roles = new ArrayList<>();

        //creation time (current moment)
        Date now = new Date();
        //date to expired
        Date expiredTime = new Date(now.getTime() + tokenValidTimeMilliseconds * 1000);
        return JWT.create()
                .withSubject(username)
                .withClaim("roles" , roles)
                .withIssuedAt(now)
                .withExpiresAt(expiredTime)
                .sign(algorithm);
    }

    //auth хранится в контексте - получить с помощью юзера
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        //return JWT.decode(token).getSubject();
        String res = jwtVerifier.verify(token).getSubject();
        return res;
    }

    public boolean validateToken(String token) {
        try {
            jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();

            //до текущей даты
           // return !JWT.decode(token).getExpiresAt().before(new Date());
            return !jwtVerifier.verify(token).getExpiresAt().before(new Date());
        } catch (JWTVerificationException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Jwt token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    private List<String> getRoleNames(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> role.getRoles().name())
                .collect(Collectors.toList());
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(tokenHeader);
    }
}
