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

    @Autowired
    private lateinit var firebase : FirebaseInitialization

    fun guardarGrupo(grupo: String) {
        grupoRepository.save(grupo)
    }

    /*** Metodo creado para probar la base de datos **/
    fun retornarTodosLosGrupos() : List<Grupo>{
        return grupoRepository.retornarTodosLosGruposFirebase()
    }

    /*** Metodo creado para probar la base de datos **/
    fun guardarGrupoFirebase(grupo: Grupo) : Boolean{
        return grupoRepository.guardarGrupoFirebase(grupo)
    }

    private fun getCollection() = firebase.getFirestore().collection("groups")
}
