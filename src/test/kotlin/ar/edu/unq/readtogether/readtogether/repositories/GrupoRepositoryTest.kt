package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.excepciones.EntidadNoEncontradaException
import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Libro
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
        val grupo = Grupo("dalasha", "son muy buenos", mutableListOf("jorge"))
        grupoRepository.save(grupo)
        val id = grupoRepository.obtenerGrupos(grupo.nombre).first().id

        grupo.nombre = "ex-dalasha"
        grupo.descripcion = "eran muy buenos"
        grupo.usuarios.add("pepe")
        grupoRepository.actualizarGrupo(grupo)

        val grupoRecuperado = grupoRepository.obtenerGrupoDeID(id)
        assertThat(grupoRecuperado.nombre == "ex-dalasha")
        assertThat(grupoRecuperado.descripcion == "eran muy buenos")
        assertThat(grupoRecuperado.usuarios).containsAll(listOf("jorge", "pepe"))
    }

    @Test
    fun puedoObtenerLaBibliotecaDelGrupo(){
        val grupo = Grupo("dalasha", "son muy buenos", mutableListOf())
        grupoRepository.save(grupo)
        val id = grupoRepository.obtenerGrupos(grupo.nombre).first().id
        val libro = Libro("Un libro", "Un Autor", "Link")
        grupo.agregarLibro(libro)

        assertThat(grupoRepository.obtenerBibliotecaDeGrupo(id).isNotEmpty())
    }

    @Test
    fun cuandoRecuperoUnGrupoInexistente_seLevantaUnaExcepcion() {
        val idInexistente = "123=!"

        assertThatThrownBy{ grupoRepository.obtenerGrupoDeID(idInexistente) }
                .isInstanceOf(EntidadNoEncontradaException::class.java)
                .hasMessage("No se encontró el grupo con id %s", idInexistente)
    }
}