package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import org.hamcrest.Matchers
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
class TestBuscador {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var grupoService: GrupoService


    @Test
    fun cuandoBuscoPorUnNombre_elBuscadorDevuelveLosGruposConEseNombre() {
        grupoService.crearGrupo("La comunidad del anillo", "Descripción")
        grupoService.crearGrupo("Amantes de Crepusculo", "Descripcion")

        mockMvc.perform(MockMvcRequestBuilders.get("/grupos?busqueda=comunidad"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.[0].nombre", Matchers.comparesEqualTo("La comunidad del anillo")))
                .andExpect(jsonPath("$", hasSize<Int>(1)))
    }

    @AfterEach
    internal fun tearDown() {
        grupoService.eliminarDatos()
    }
}