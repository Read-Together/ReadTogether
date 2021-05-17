package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import ar.edu.unq.readtogether.readtogether.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GrupoService @Autowired constructor(
    private val grupoRepository: GrupoRepository,
    private val usuarioRepository: UsuarioRepository
) {


    fun guardarGrupo(grupo: Grupo) {
        grupoRepository.save(grupo)
    }

    fun obtenerGruposConNombre(nombre : String) : List<Grupo>{
        return grupoRepository.obtenerGrupos(nombre)
    }

    fun obtenerGruposConDescripcion(descripcion:String):List<Grupo>{
        return grupoRepository.obtenerGrupos(descripcion)
    }

    fun eliminarDatos(){
        grupoRepository.eliminarDatos()
    }

    fun crearGrupo(nombre: String, detalle: String) {
        val grupo1 = Grupo(nombre,detalle)
        this.guardarGrupo(grupo1)
    }

    fun suscribirUsuarioAlGrupo(usuario: String, nombreDelGrupo: String) {
        val grupo = this.obtenerGruposConNombre(nombreDelGrupo).first()

        grupo.agregarUsuario(usuarioRepository.usuarioDeNombre(usuario))
    }
}
