package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.firebase.FirebaseInitialization
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
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

    fun retornarGruposFirebase() : List<Grupo>{
        var retorno = mutableListOf<Grupo>()
        lateinit var grupo: Grupo
        var collection : ApiFuture<QuerySnapshot> = getCollection().get()
        return try {
            for (doc in collection.get().documents){
                grupo = doc.toObject(Grupo::class.java)
                grupo.id = doc.id
                retorno.add(grupo)
            }
            retorno
        } catch (e: Exception){
            return emptyList()
        }
    }

    /*** Metodo creado para probar la base de datos **/
    fun agregarGrupoFirebase(grupo: Grupo) : Boolean{
        var docData: MutableMap<String, Any> = mutableMapOf()
        docData["nombre"] = grupo.nombre
        docData["descripcion"] = grupo.descripcion

        var groups = getCollection()
        var writeResultApiFuture: ApiFuture<WriteResult> = groups.document().create(docData)

        try {
            if (writeResultApiFuture.get() != null){
                return true
            }
            return false
        } catch (e: Exception){
            return false
        }
    }

    private fun getCollection() = firebase.getFirestore().collection("groups")
}
