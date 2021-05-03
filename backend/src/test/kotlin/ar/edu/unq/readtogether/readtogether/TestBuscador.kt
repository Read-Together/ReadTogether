package ar.edu.unq.readtogether.readtogether

import ar.edu.unq.readtogether.readtogether.firebase.FirebaseInitialization
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import org.hamcrest.Matchers
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
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

    @Test
    fun cuandoBuscoPorUnNombre_elBuscadorDevuelveLosGruposConEseNombre() {
        val grupoQueMatchea = "La comunidad del anillo"
        val grupoQueNoMatchea = "Amantes de Crepusculo"
        grupoService.guardarGrupo(grupoQueMatchea)
        grupoService.guardarGrupo(grupoQueNoMatchea)

        mockMvc.perform(MockMvcRequestBuilders.get("/grupos?busqueda=comunidad"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.[0]", Matchers.comparesEqualTo(grupoQueMatchea)))
                .andExpect(jsonPath("$", hasSize<Int>(1)))
    }

    @Test
    fun cuandoPidoTodosLosGrupos_retornaDosPorAhora(){
        var grupos = grupoService.retornarTodosLosGrupos()
        assertEquals(2, grupos.size)
    }
}