package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioTest {

    private lateinit var usuario: Usuario

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @BeforeAll
    fun setUp() {
        usuarioService.eliminarDatos()
    }

    @AfterEach
    fun clean() {
        usuarioService.eliminarDatos()
    }

    @Test
    fun cuandoRegistroUnUsuario_puedoEncontralo() {
        usuario = Usuario("mauro10", "mauro@gmail.com", "1234")
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar")
                .content(ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON))
        var user = usuarioService.buscarUsuario(usuario)
        assert(user!!.userName == usuario.userName)
    }

    @Test
    fun cuandoAgregoUnUsuario_retornaUn200() {
        usuario = Usuario("gonzalo1994", "gonzalo@gmail.com", "1234")
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar")
                .content(ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun cuandoAgregoUnUsuarioQueYaExiste_retornaUn400() {
        usuario = Usuario("juan", "juan@gmail.com", "1234")
        usuarioService.registrarUsuario(usuario)
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar")
                .content(ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun cuandoInicioSesion_retornaUn200() {
        usuario = Usuario("juan2", "juan2@gmail.com", "1234")
        usuarioService.registrarUsuario(usuario)
        var request = RequestUsuario("juan2", "1234")
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun cuandoInicioSesion_MeDevuelveUnToken() {
        usuario = Usuario("barbi", "barbi@gmail.com", "1234")
        usuarioService.registrarUsuario(usuario)
        var request = RequestUsuario("barbi", "1234")
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response.contentAsString
        assert(response.contains("Bearer "))
    }

    @Test
    fun cuandoInicioSesionConDatosErroneos_retornaForbidden() {
        usuario = Usuario("juan3", "juan3@gmail.com", "1234")
        usuarioService.registrarUsuario(usuario)
        var usuarioLogin = RequestUsuario("juan3", "123")
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(ObjectMapper().writeValueAsString(usuarioLogin))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden)
    }

}
