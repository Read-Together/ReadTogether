package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GrupoService @Autowired constructor(private val grupoRepository: GrupoRepository) {


    fun guardarGrupo(grupo: Grupo) {
        grupoRepository.save(grupo)
    }

    fun obtenerGruposConNombre(nombre : String) : List<Grupo>{
        return grupoRepository.obtenerGrupos(nombre)
    }

    fun eliminarDatos(){
        grupoRepository.eliminarDatos()
    }

    fun crearGrupo(nombre: String, detalle: String) {
        val grupo1 = Grupo(nombre,detalle)
        this.guardarGrupo(grupo1)
    }
}
