package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Libro
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.*
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


    @BeforeEach
    fun setUp(){
        usuarioService.eliminarDatos()
        grupoService.eliminarDatos()
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

    @Test
    fun cuandoUnUsuarioSaleDeUnGrupo_luegoNoEstaEnLaListaDeUsuariosDelGrupo() {
        val idDelGrupo = grupoService.crearGrupo("nombre2", "descripcion2")
        val userName = "gustavo"
        val password = "123"
        val usuario = Usuario(userName,"gustavo@gmail.com", password)
        val token = registrarYLogear(usuario)
        grupoService.suscribirUsuarioAlGrupo(userName, idDelGrupo)

        mockMvc.perform(
                MockMvcRequestBuilders.post("/grupos/$idDelGrupo/salir")
                        .header("Authorization", token)
                        .content(JSONObject().put("userName", usuario.userName).toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful)

        assertThat(grupoService.obtenerGrupoDeID(idDelGrupo).usuarios).doesNotContain(usuario.userName)
    }

    @Test
    fun cuandoSacoAUnUsuarioDeUnGrupoEnElQueNoEstaba_retornaUnError() {
        val idDelGrupo = grupoService.crearGrupo("nombre", "descripcion")
        val userName = "ernesto"
        val password = "123"
        val usuario = Usuario(userName,"ernesto@gmail.com", password)
        val token = registrarYLogear(usuario)

        mockMvc.perform(
                MockMvcRequestBuilders.post("/grupos/$idDelGrupo/salir")
                        .header("Authorization", token)
                        .content(JSONObject().put("userName", usuario.userName).toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun cuandoSacoAUnUsuarioDistintoAlQueObtuvoElToken_retornaUnError() {
        val idDelGrupo = grupoService.crearGrupo("nombre", "descripcion")
        val userName = "ernesto"
        val password = "123"
        val usuarioLogeado = Usuario(userName,"ernesto@gmail.com", password)
        usuarioService.registrarUsuario(usuarioLogeado)

        val usuarioASacar = Usuario("otroUsername","otro@gmail.com", password)
        usuarioService.registrarUsuario(usuarioASacar)
        grupoService.suscribirUsuarioAlGrupo(usuarioASacar.userName, idDelGrupo)
        val token = usuarioService.login(RequestUsuario(usuarioLogeado.userName, usuarioLogeado.password))

        mockMvc.perform(
                MockMvcRequestBuilders.post("/grupos/$idDelGrupo/salir")
                        .header("Authorization", token)
                        .content(JSONObject().put("userName", usuarioASacar.userName).toString())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    private fun registrarYLogear(usuario: Usuario): String {
        usuarioService.registrarUsuario(usuario)
        val token = usuarioService.login(RequestUsuario(usuario.userName, usuario.password))
        return token
    }
    @Test
    fun pidoLaBibliotecaDeUnGrupo_retornaUn2xx(){
        var usuario = Usuario("barbi","barbi@gmail.com","123")
        usuarioService.registrarUsuario(usuario)
        val usuarioRequest = RequestUsuario("barbi","123")
        val token = usuarioService.login(usuarioRequest)
        val grupo = Grupo("Comunidad", "esto es una descripcion", mutableListOf())
        val idDelGrupo = grupoService.guardarGrupo(grupo)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/grupos/$idDelGrupo/biblioteca")
                .header("Authorization",token))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)

    }

    @Test
    fun pídoBibliotecaDeUnGrupo_retornaUnaListaConUnElemento(){
        var usuario = Usuario("barbi","barbi@gmail.com","123")
        usuarioService.registrarUsuario(usuario)
        val usuarioRequest = RequestUsuario("barbi","123")
        val token = usuarioService.login(usuarioRequest)
        val grupo = Grupo("Comunidad", "esto es una descripcion", mutableListOf())
        val libro = Libro("Un libro", mutableListOf("Un Autor"), "Link")
        val idDelGrupo = grupoService.guardarGrupo(grupo)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/grupos/$idDelGrupo/biblioteca")
                .header("Authorization",token)
                .content(ObjectMapper().writeValueAsString(libro))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful)

        val biblioteca =  mockMvc.perform(
            MockMvcRequestBuilders.get("/grupos/$idDelGrupo/biblioteca")
                .header("Authorization",token))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andReturn().response.contentAsString

        val contieneNombre = biblioteca.contains(libro.nombre)
        val contieneLink = biblioteca.contains(libro.link)
        assertThat(contieneNombre && contieneLink)
    }
}