package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.firebase.FirebaseInitialization
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GrupoService {

    @Autowired
    private lateinit var grupoRepository: GrupoRepository

    fun guardarGrupo(grupo: Grupo) {
        grupoRepository.save(grupo)
    }

    fun obtenerGruposConNombre(nombre : String) : List<Grupo>{
        return grupoRepository.obtenerGrupos(nombre)
    }

    fun eliminarDatos(){
        grupoRepository.eliminarDatos()
    }
}