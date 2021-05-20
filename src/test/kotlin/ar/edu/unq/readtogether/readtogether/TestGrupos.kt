package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.controllers.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.controllers.UsuariosController
import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestGrupos {

    @Autowired
    private lateinit var usuarioService: UsuarioService
    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var usuario: Usuario

    @Autowired
    private lateinit var grupoService: GrupoService
    @Autowired
    private lateinit var userService: UsuarioService
    @Autowired
    private lateinit var usuarioController: UsuariosController

    @BeforeAll
    fun setUp(){
        userService.eliminarDatos()
    }

    @AfterEach
    fun clean(){
        userService.eliminarDatos()
        grupoService.eliminarDatos()
    }

    @Test
    fun unUsuarioCreaUnGrupo() {
        val creacionDeGruposForm = CreacionDeGruposForm("grupo", "detalle")
        val usuarioARegistrar = Usuario("mauro","mauro@gmail.com", "123")
        val usuarioQueIniciaSesion = RequestUsuario("mauro", "123")
        userService.registrarUsuario(usuarioARegistrar)
        var token = userService.login(usuarioQueIniciaSesion)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/grupos")
                .header("Authorization", token)
                .content(ObjectMapper().writeValueAsString(creacionDeGruposForm))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
        assertThat(grupoService.obtenerGruposConNombre("grupo")).hasSize(1)
        assertThat(grupoService.obtenerGruposConDescripcion("detalle")).hasSize(1)
    }

    @Test
    fun unUsuarioSeUneAlGrupoYQuedaRegistradoEnEl(){
        usuario = Usuario("gonzalo1995","gonzalo2@gmail.com","1234")
        usuarioService.registrarUsuario(usuario)
        val nombreComunidad = "comunidad del anillo"
        grupoService.guardarGrupo(Grupo(nombreComunidad, "mi precioso", mutableListOf()))
        val idDelGrupo = grupoService.obtenerGruposConNombre(nombreComunidad)[0].id

        mockMvc.perform(
            MockMvcRequestBuilders.post("/grupos/$idDelGrupo/registrar")
                .content(JSONObject().put("userName", usuario.userName).toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        assertThat(grupoService.obtenerGrupoDeID(idDelGrupo).usuarios).hasSize(1)
    }

}