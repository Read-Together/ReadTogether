package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import ar.edu.unq.readtogether.readtogether.controllers.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.After
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class CrearComunidadAutenticadoDefs : SpringIntegrationTest(){

    private var token: String = ""
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var usuarioService: UsuarioService
    @Autowired
    private lateinit var context: StepDefinitionsContext
    @Autowired
    private lateinit var grupoService: GrupoService

    private val username = "unNombreDeUsuario"
    private val contraseña = "unaContrasenia"
    private val nombreDeComunidad = "nombre"
    private val descripcionDeComunidad = "descripcion"

    @After
    fun limpiar(){
        usuarioService.eliminarDatos()
        grupoService.eliminarDatos()
    }

    @And("esta autenticado")
    fun autenticarUsuario(){
        val request = RequestUsuario(username, contraseña)
        context.perform(
                MockMvcRequestBuilders.post("/login")
                        .content(ObjectMapper().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON), mockMvc
        )
        token = context.andReturn().response.contentAsString
    }

    @When("intenta crear una comunidad")
    fun `crear una comunidad`(){
        val creacionDeGruposForm = CreacionDeGruposForm(nombreDeComunidad, descripcionDeComunidad)

        context.perform(
                MockMvcRequestBuilders.post("/grupos")
                        .header("Authorization", token)
                        .content(ObjectMapper().writeValueAsString(creacionDeGruposForm))
                        .contentType(MediaType.APPLICATION_JSON), mockMvc)
    }

    @Then("la aplicacion se lo permite")
    fun verificarQueSeCreoLaComunidad(){
        context.andExpect(status().isOk)

        val gruposEncontrados = grupoService.obtenerGruposConNombre(nombreDeComunidad)
        assertThat(gruposEncontrados).hasSize(1)
        assertThat(gruposEncontrados.first().nombre).isEqualTo(nombreDeComunidad)
        assertThat(gruposEncontrados.first().descripcion).isEqualTo(descripcionDeComunidad)
    }

    @Then("la aplicacion no se lo permite")
    fun verificarQueNoSePudoCrearLaComunidad(){
        context.andExpect(status().isForbidden)
    }
}