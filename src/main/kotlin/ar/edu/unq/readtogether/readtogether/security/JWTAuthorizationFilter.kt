package ar.edu.unq.readtogether.readtogether.security

import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Clase en la que se define el filtro por el
 * cual tienen que pasar los endpoint.
 * **/
class JWTAuthorizationFilter : OncePerRequestFilter() {

    private val HEADER = "Authorization"
    private val PREFIX = "Bearer "
    private val SECRET = "mySecretKey"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            if (existeJWTToken(request, response)) {
                val claims: Claims = validateToken(request)
                if (claims["authorities"] != null) {
                    setUpSpringAuthentication(claims)
                } else {
                    SecurityContextHolder.clearContext()
                }
            } else {
                SecurityContextHolder.clearContext()
            }
            chain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            catchError(response, e)
            return
        } catch (e: UnsupportedJwtException) {
            catchError(response, e)
            return
        } catch (e: MalformedJwtException) {
            catchError(response, e)
            return
        }
    }

    private fun catchError(response: HttpServletResponse, e: Exception) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
    }

    private fun validateToken(request: HttpServletRequest): Claims {
        val jwtToken = request.getHeader(HEADER).replace(PREFIX, "")
        return Jwts.parser().setSigningKey(SECRET.toByteArray()).parseClaimsJws(jwtToken).body
    }

    private fun setUpSpringAuthentication(claims: Claims) {
        val authorities: List<String> = claims["authorities"] as List<String>
        val auth = UsernamePasswordAuthenticationToken(claims.subject, null,
                authorities.stream().map { role: String? -> SimpleGrantedAuthority(role) }.collect(Collectors.toList()))
        SecurityContextHolder.getContext().authentication = auth
    }

    private fun existeJWTToken(request: HttpServletRequest, res: HttpServletResponse): Boolean {
        val authenticationHeader = request.getHeader(HEADER)
        return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
    }

}
