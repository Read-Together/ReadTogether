package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GrupoRepositoryTest {

    @Autowired
    private lateinit var grupoRepository: GrupoRepository

    @AfterAll
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
        assertThat(grupoRecuperado.nombre == "ex-dalasha")
        assertThat(grupoRecuperado.descripcion == "eran muy buenos")
    }
}