package net.kingborn.erp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.kingborn.core.exception.BizException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key;

    private long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String createJwt(String id, String subject, String roles) {
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);

        if (ttl > 0) {
            builder.setExpiration(new Date(System.currentTimeMillis() + ttl));
        }

        return builder.compact();
    }

    public Claims parseJwt(String jwt) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();

        } catch (Exception e) {
            return null;
        }

    }

}
