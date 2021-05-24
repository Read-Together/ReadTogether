package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrupoRepositoryTest {

    @Autowired
    private lateinit var grupoRepository: GrupoRepository

    @AfterEach
    fun tearDown() {
        grupoRepository.eliminarDatos()
    }

    @Test
    fun puedoActualizarUnGrupo(){
        val grupo = Grupo("dalasha", "son muy buenos", mutableListOf())
        grupoRepository.save(grupo)
        val id = grupoRepository.obtenerGrupos(grupo.nombre).first().id

        grupo.nombre = "ex-dalasha"
        grupo.descripcion = "eran muy buenos"
        grupoRepository.actualizarGrupo(grupo)

        val grupoRecuperado = grupoRepository.obtenerGrupoDeID(id)
        assertEquals(grupoRecuperado.nombre, "ex-dalasha")
        assertEquals(grupoRecuperado.descripcion, "eran muy buenos")
    }
}