package ar.edu.unq.readtogether.readtogether.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import java.util.stream.Collectors


class Token {

    /**
     * Se le agrega "Bearer" como prefijo al token por ser un
     * estandar de OAUTH 2.0.
     * "Bearer" avisa que lo que lo acompaña detras es un token de tipo JWT
     * **/
    fun getJWTToken(username: String): String {
        val secretKey = "mySecretKey"
        val grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER")
        val token = Jwts
                .builder()
                .setSubject(username)
                .claim("authorities", grantedAuthorities.stream()
                        .map { obj: GrantedAuthority -> obj.authority }
                        .collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS512, secretKey.toByteArray()).compact()

            return "Bearer $token"
        }

}