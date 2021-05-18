package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.After
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class RegistrarDefs : SpringIntegrationTest(){

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var context: StepDefinitionsContext

    val username = "unNombreDeUsuario"
    val email = "unMail@gmail.com"
    val contraseña = "unaContrasenia"

    @After
    fun limpiar(){
        usuarioService.eliminarDatos()
    }

    @Given("un usuario que no se ha registrado en la aplicacion")
    fun `el usuario no esta registrado en la aplicacion`(){
        val request = RequestUsuario(username, contraseña)
        context.perform((MockMvcRequestBuilders.post("/login")
                .content(ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)), mockMvc)
        context.andExpect(status().isNotFound)
    }

    @When("se registra con usuario, mail y contraseña")
    fun `registrar al usuario`(){
        val usuario = Usuario(username, email, contraseña)

        context.perform(MockMvcRequestBuilders.post("/registrar")
                .content(ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON), mockMvc
        )
    }

    @Then("puede autenticarse con las credenciales con las que se registro")
    fun autenticarAlUsuario(){
        context.andExpect(status().isOk)

        val request = RequestUsuario(username, contraseña)
        context.perform((MockMvcRequestBuilders.post("/login")
                .content(ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)), mockMvc)
        context.andExpect(status().isOk)
    }

}