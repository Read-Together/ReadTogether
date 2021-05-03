package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.firebase.FirebaseInitialization
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import org.hamcrest.Matchers
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    private var grupo1 = Grupo()
    private var grupo2 = Grupo()


    @Test
    fun cuandoBuscoPorUnNombre_elBuscadorDevuelveLosGruposConEseNombre() {
        mockMvc.perform(MockMvcRequestBuilders.get("/grupos?busqueda=comunidad"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.[0].nombre", Matchers.comparesEqualTo("La comunidad del anillo")))
                .andExpect(jsonPath("$", hasSize<Int>(1)))
    }

}