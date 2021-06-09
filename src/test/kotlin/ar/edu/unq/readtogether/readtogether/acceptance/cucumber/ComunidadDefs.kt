package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@AutoConfigureMockMvc
class ComunidadDefs : SpringIntegrationTest() {

    private val nombreGrupo = "Fans de Tolkien"
    private val descripcionGrupo = "Un grupo de hobbits"
    private lateinit var idDelGrupo: String

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var grupoService: GrupoService

    @Autowired
    private lateinit var context: StepDefinitionsContext

    @Given("una comunidad ya creada de nombre \"([^\"]*)\"$")
    fun crearComunidadDeNombre(nombre: String) {
        idDelGrupo = grupoService.crearGrupo(nombre, "una descripcion")
    }

    @When("crea una comunidad")
    fun crearUnaComunidad() {
        val creacionDeGruposForm = CreacionDeGruposForm(nombreGrupo, descripcionGrupo)
        context.perform(
                post("/grupos")
                        .header("Authorization", context.token)
                        .content(ObjectMapper().writeValueAsString(creacionDeGruposForm))
                        .contentType(MediaType.APPLICATION_JSON), mockMvc)
    }

    @Given("el usuario pertenece a la comunidad \"([^\"]*)\"$")
    @When("el usuario se une a la comunidad \"([^\"]*)\"$")
    fun usuarioSeUneAComunidad(nombreDeComunidad: String) {
        val id = grupoService.obtenerGruposConNombre(nombreDeComunidad).first().id
        context.perform(post("/grupos/${id}/registrar")
                .header("Authorization", context.token)
                .content(JSONObject().put("userName", username).toString())
                .contentType(MediaType.APPLICATION_JSON), mockMvc
        )
    }

    @When("el usuario sale de la comunidad \"([^\"]*)\"$")
    fun sacarUsuarioDeComunidad(nombreDeComunidad: String) {
        val id = grupoService.obtenerGruposConNombre(nombreDeComunidad).first().id
        context.perform(post("/grupos/${id}/salir")
                .header("Authorization", context.token)
                .content(JSONObject().put("userName", username).toString())
                .contentType(MediaType.APPLICATION_JSON), mockMvc
        )
    }

    @Then("obtiene el id de la comunidad creada")
    fun assertarQueObtieneIdDeUnaComunidad() {
        idDelGrupo = context.andReturn().response.contentAsString
        val grupoConId = grupoService.obtenerGrupoDeID(idDelGrupo)
        assertThat(grupoConId.nombre).isEqualTo(nombreGrupo)
        assertThat(grupoConId.descripcion).isEqualTo(descripcionGrupo)
    }

    @Then("es miembro del grupo")
    fun assertarQueElUsuarioEsMiembroDelGrupo() {
        val grupoConId = grupoService.obtenerGrupoDeID(idDelGrupo)
        assertThat(grupoConId.usuarios).contains(username)
    }

    @Then("el usuario ya no pertenece a la comunidad \"([^\"]*)\"$")
    fun assertarQueElUsuarioNoEsMiembroDelGrupo(nombreDeComunidad: String) {
        val grupo = grupoService.obtenerGruposConNombre(nombreDeComunidad).first()
        assertThat(grupo.usuarios).doesNotContain(username)
    }

}