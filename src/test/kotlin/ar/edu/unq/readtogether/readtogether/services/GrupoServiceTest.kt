package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.excepciones.EntidadNoEncontradaException
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GrupoServiceTest {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var grupoService: GrupoService

    @AfterEach
    internal fun tearDown() {
        usuarioService.eliminarDatos()
        grupoService.eliminarDatos()
    }

    @Test
    fun cuandoQuitoAUnUsuarioDeUnGrupo_luegoEsteNoEstaEnLaListaDeMiembros() {
        val idDelGrupo = grupoService.crearGrupo("nombre", "descripcion")
        val userName = "ernesto"
        val password = "123"
        val usuario = Usuario(userName, "ernesto@gmail.com", password)
        usuarioService.registrarUsuario(usuario)
        grupoService.suscribirUsuarioAlGrupo(userName, idDelGrupo)

        grupoService.quitarUsuarioDelGrupo(userName, idDelGrupo)

        assertThat(grupoService.obtenerGrupoDeID(idDelGrupo).usuarios).doesNotContain(usuario.userName)
    }

    @Test
    fun cuandoQuitoAUnUsuarioDeUnGrupoEnElQueNoEsta_seLevantaUnaExcepcion() {
        val idDelGrupo = grupoService.crearGrupo("nombre", "descripcion")
        val userName = "ernesto"
        val password = "123"
        val usuario = Usuario(userName, "ernesto@gmail.com", password)
        usuarioService.registrarUsuario(usuario)!!.id

        assertThatThrownBy { grupoService.quitarUsuarioDelGrupo(userName, idDelGrupo) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("El usuario %s no está en el grupo %s", userName, idDelGrupo)

    }

    @Test
    fun cuandoQuitoAUnUsuarioQueNoExisteDeUnGrupo_seLevantaUnaExcepcion() {
        val idDelGrupo = grupoService.crearGrupo("nombre", "descripcion")
        val userName = "usuarioinexistente"

        assertThatThrownBy { grupoService.quitarUsuarioDelGrupo(userName, idDelGrupo) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("El usuario %s no está en el grupo %s", userName, idDelGrupo)
    }

    @Test
    fun cuandoQuitoAUnUsuarioDeUnGrupoQueNoExiste_seLevantaUnaExcepcion() {
        val userName = "ernesto"
        val password = "123"
        val usuario = Usuario(userName, "ernesto@gmail.com", password)
        usuarioService.registrarUsuario(usuario)!!.id

        val idInexistente = "123!"
        assertThatThrownBy { grupoService.quitarUsuarioDelGrupo(userName, idInexistente) }
                .isInstanceOf(EntidadNoEncontradaException::class.java)
                .hasMessage("No se encontró el grupo con id %s", idInexistente)
    }
}