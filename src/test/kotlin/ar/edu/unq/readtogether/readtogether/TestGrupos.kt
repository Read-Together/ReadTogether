package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.modelo.Grupo
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
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var grupoService: GrupoService
    @Autowired
    private lateinit var usuarioService: UsuarioService


    @BeforeAll
    fun setUp(){
        usuarioService.eliminarDatos()
    }

    @AfterEach
    fun clean(){
        usuarioService.eliminarDatos()
        grupoService.eliminarDatos()
    }

    @Test
    fun unUsuarioCreaUnGrupo() {
        val creacionDeGruposForm = CreacionDeGruposForm("grupo", "detalle")
        val usuarioARegistrar = Usuario("mauro","mauro@gmail.com", "123")
        val usuarioQueIniciaSesion = RequestUsuario("mauro", "123")
        usuarioService.registrarUsuario(usuarioARegistrar)
        var token = usuarioService.login(usuarioQueIniciaSesion)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/grupos")
                .header("Authorization", token)
                .content(ObjectMapper().writeValueAsString(creacionDeGruposForm))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
        assertThat(grupoService.obtenerGruposConNombre("grupo")).hasSize(1)
        assertThat(grupoService.obtenerGruposConDescripcion("detalle")).hasSize(1)
    }

    @Test
    fun unUsuarioSeUneAlGrupoYQuedaRegistradoEnEl(){
        var usuario = Usuario("gonzalo1995","gonzalo2@gmail.com","1234")
        usuarioService.registrarUsuario(usuario)
        val usuarioRequest = RequestUsuario("gonzalo1995","1234")
        val nombreComunidad = "comunidad del anillo"
        var token = usuarioService.login(usuarioRequest)
        grupoService.guardarGrupo(Grupo(nombreComunidad, "mi precioso", mutableListOf()))
        var grupo = grupoService.obtenerGruposConNombre(nombreComunidad)[0]
        val idDelGrupo = grupo.id

        mockMvc.perform(
            MockMvcRequestBuilders.post("/grupos/$idDelGrupo/registrar")
                .header("Authorization",token)
                .content(JSONObject().put("userName", usuario.userName).toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful)

        var usuariosDelGrupo = grupoService.obtenerGrupoDeID(idDelGrupo).usuarios
        assertThat(usuariosDelGrupo.size == 1)
        assertThat(usuariosDelGrupo.contains(usuario))
    }

    @Test
    fun buscoUnaComunidadPorUnIdYLoRetorna(){
        var usuario = Usuario("barbi","barbi@gmail.com","123")
        usuarioService.registrarUsuario(usuario)
        val usuarioRequest = RequestUsuario("barbi","123")
        val nombreComunidad = "silicon valley"
        val grupo = Grupo(nombreComunidad, "esto es una descripcion", mutableListOf())
        val token = usuarioService.login(usuarioRequest)
        val idDelGrupo = grupoService.guardarGrupo(grupo)

        var response = mockMvc.perform(
            MockMvcRequestBuilders.get("/grupos/$idDelGrupo")
                .header("Authorization",token))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andReturn().response.contentAsString
        var contieneId = response.contains(grupo.id)
        var contieneNombre = response.contains(grupo.nombre)
        var contieneDescripcion = response.contains(grupo.descripcion)
        assertThat(contieneId && contieneNombre && contieneDescripcion)
    }

}