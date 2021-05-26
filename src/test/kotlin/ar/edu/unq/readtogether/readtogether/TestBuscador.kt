package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import org.hamcrest.Matchers
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestBuscador {

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
    fun cuandoBuscoPorUnNombre_elBuscadorDevuelveLosGruposConEseNombre() {
        grupoService.crearGrupo("La comunidad del anillo", "Descripción")
        grupoService.crearGrupo("Amantes de Crepusculo", "Descripcion")
        val usuarioARegistrar = Usuario("mauro","mauro@gmail.com", "123")
        val usuarioQueIniciaSesion = RequestUsuario("mauro", "123")
        usuarioService.registrarUsuario(usuarioARegistrar)
        var token = usuarioService.login(usuarioQueIniciaSesion)
        mockMvc.perform(MockMvcRequestBuilders.get("/grupos?busqueda=comunidad")
                .header("Authorization", token))
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.[0].nombre", Matchers.comparesEqualTo("La comunidad del anillo")))
                .andExpect(jsonPath("$", hasSize<Int>(1)))
    }

}