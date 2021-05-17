package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
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
class TestGrupos {


    @Autowired
    private lateinit var mockMvc: MockMvc

    private lateinit var usuario: Usuario

    @Autowired
    private lateinit var grupoService: GrupoService

    @Test
    fun unUsuarioCreaUnGrupo() {
        val creacionDeGruposForm = CreacionDeGruposForm("grupo", "detalle")
        mockMvc.perform(
            MockMvcRequestBuilders.post("/grupos")
                .content(ObjectMapper().writeValueAsString(creacionDeGruposForm))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
        assertThat(grupoService.obtenerGruposConNombre("grupo")).hasSize(1)
        assertThat(grupoService.obtenerGruposConDescripcion("detalle")).hasSize(1)
    }

    @Test
    fun unUsuarioSeUneAlGrupoYQuedaRegistradoEnEl(){
        usuario = Usuario("gonzalo1994","gonzalo@gmail.com","1234")
        val nombreComunidad = "comunidad del anillo"
        grupoService.guardarGrupo(Grupo(nombreComunidad, "mi precioso"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/grupos/" + nombreComunidad + "/registrar")
                .content(JSONObject().put("userName", usuario.userName).toString())
                .contentType(MediaType.APPLICATION_JSON)
        )

        assertThat(grupoService.obtenerGruposConNombre("comunidad del anillo")).hasSize(1)
        assertThat(grupoService.obtenerGruposConNombre("comunidad del anillo").first().usuarios).hasSize(1)
    }

    @AfterEach
    internal fun tearDown() {
        grupoService.eliminarDatos()
    }
}