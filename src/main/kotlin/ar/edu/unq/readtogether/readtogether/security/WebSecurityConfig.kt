package ar.edu.unq.readtogether.readtogether.security

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter(){

    /**
     * Acá está la configuración donde permite el acceso
     * a los endpoint y pide autorización a los restantes
     * **/
    override fun configure(http: HttpSecurity){
        http.csrf().disable()
                .addFilterAfter(JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/registrar").permitAll()
                .antMatchers(HttpMethod.GET, "/grupos").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
    }
}