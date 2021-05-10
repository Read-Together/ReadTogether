package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.repositories.UsuarioRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.junit.jupiter.api.Test
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
class UsuarioTest {

    private lateinit var usuario: Usuario
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var service: UsuarioService
    @Autowired
    private lateinit var repositorio: UsuarioRepository

    @AfterEach
    fun limpiarBase(){
        service.eliminarDatos()
    }

    @Test
    fun cuandoAgregoUnUsuario_retornaUn200() {
        usuario = Usuario("gonzalo1994","gonzalo@gmail.com","1234")
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar")
                .content(ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun cuandoAgregoUnUsuarioQueYaExiste_retornaUn400(){
        usuario = Usuario("gonzalo1994","gonzalo@gmail.com","1234")
        service.registrarUsuario(usuario)
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar")
                .content(ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun comprobarQueElUsuarioFueRegistrado(){
        usuario = Usuario("gonzalo1994","gonzalo@gmail.com","1234")
        mockMvc.perform(MockMvcRequestBuilders.post("/registrar")
                .content(ObjectMapper().writeValueAsString(usuario))
                .contentType(MediaType.APPLICATION_JSON))
        var user = repositorio.buscarUsuario(usuario)
       assert(user!!.userName == usuario.userName)
    }

}