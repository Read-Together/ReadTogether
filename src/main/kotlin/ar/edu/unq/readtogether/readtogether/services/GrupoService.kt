package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Libro
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import ar.edu.unq.readtogether.readtogether.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GrupoService @Autowired constructor(
        private val grupoRepository: GrupoRepository,
        private val usuarioRepository: UsuarioRepository
) {


    fun guardarGrupo(grupo: Grupo): String {
        return grupoRepository.save(grupo)
    }

    fun obtenerGruposConNombre(nombre: String): List<Grupo> {
        return grupoRepository.obtenerGrupos(nombre)
    }

    fun obtenerGruposConDescripcion(descripcion: String): List<Grupo> {
        return grupoRepository.obtenerGrupos(descripcion)
    }

    fun eliminarDatos() {
        grupoRepository.eliminarDatos()
    }

    fun crearGrupo(nombre: String, detalle: String): String {
        val grupo1 = Grupo(nombre, detalle, mutableListOf())
        return this.guardarGrupo(grupo1)
    }

    fun suscribirUsuarioAlGrupo(usuario: String, idDelGrupo: String) {
        val grupo = this.obtenerGrupoDeID(idDelGrupo)

        grupo.agregarUsuario(usuarioRepository.usuarioDeNombre(usuario).userName)
        grupoRepository.actualizarGrupo(grupo)
    }

    fun quitarUsuarioDelGrupo(userName: String, idDelGrupo: String) {
        val grupo = this.obtenerGrupoDeID(idDelGrupo)
        verificarQueUsuarioEstaEnGrupo(grupo, userName, idDelGrupo)

        grupo.quitarUsuario(userName)
        grupoRepository.actualizarGrupo(grupo)
    }

    fun obtenerGrupoDeID(idDelGrupo: String): Grupo {
        return grupoRepository.obtenerGrupoDeID(idDelGrupo)
    }

    fun comunidad(idDelGrupo: String): Grupo {
        return grupoRepository.obtenerGrupoDeID(idDelGrupo)
    }

    private fun verificarQueUsuarioEstaEnGrupo(grupo: Grupo, userName: String, idDelGrupo: String) {
        if (!grupo.usuarios.contains(userName)) {
            throw RuntimeException("El usuario ${userName} no está en el grupo ${idDelGrupo}")
        }
    }

    fun obtenerBibiliotecaDeGrupo(idDelGrupo: String): MutableList<Libro>{
        return grupoRepository.obtenerBibliotecaDeGrupo(idDelGrupo)
    }

    fun cargarLibro(idDelGrupo: String,libro: Libro) {
        val grupo = grupoRepository.obtenerGrupoDeID(idDelGrupo)
        grupo.agregarLibro(libro)
        grupoRepository.actualizarGrupo(grupo)
    }


}
