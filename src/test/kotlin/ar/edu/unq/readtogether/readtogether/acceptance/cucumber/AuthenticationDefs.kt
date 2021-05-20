package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class AuthenticationDefs {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var context : StepDefinitionsContext

    val username = "unNombreDeUsuario"
    val email = "unMail@gmail.com"
    val contraseña = "unaContrasenia"


    @Given("un usuario que ya se ha registrado en la aplicacion")
    fun registrarUsuario(){
        val usuario = Usuario(username, email, contraseña)
        usuarioService.registrarUsuario(usuario)
    }

    @When("ingresa su usuario y contraseña correctos")
    fun autenticarUsuario(){
        val request = RequestUsuario(username, contraseña)
        context.perform(MockMvcRequestBuilders.post("/login")
                .content(ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
    }

    @Then("obtiene un token con el que autenticarse")
    fun xxx(){
        context.andExpect(status().isOk)
    }

}