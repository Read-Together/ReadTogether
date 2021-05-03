package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.firebase.FirebaseInitialization
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.Query
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class GrupoRepository{

    @Autowired
    private lateinit var firebase : FirebaseInitialization

    private fun getCollection() = firebase.getFirestore().collection("grupos")

    fun save(grupo: Grupo): String{
        var docData: MutableMap<String, Any> = mutableMapOf()
        docData["nombre"] = grupo.nombre
        docData["descripcion"] = grupo.descripcion
        var groups = getCollection()
        var writeResultApiFuture: ApiFuture<WriteResult> = groups.document().create(docData)
        try {
            if (writeResultApiFuture.get() != null){
                return "El grupo ${grupo.nombre} ha sido creado"
            }
            return "El grupo ${grupo.nombre} ha sido creado"
        } catch (e: Exception){
            return "El grupo ${grupo.nombre} NO ha sido creado"
        }
    }

    fun obtenerGrupos(terminoDeBusqueda: String): List<Grupo> {
        var retorno = mutableListOf<Grupo>()
        lateinit var grupo: Grupo
        var collection : ApiFuture<QuerySnapshot> = getCollection().get()
            for (doc in collection.get().documents){
                grupo = doc.toObject(Grupo::class.java)
                grupo.id = doc.id
                retorno.add(grupo)
            }
            return retorno.filter { grupo -> grupo.nombre.contains(terminoDeBusqueda) }
    }

    /***metodo de prueba ***/
    fun eliminarDatos(){
        var collection = getCollection().listDocuments()
        for (doc in collection){
            doc.delete()
        }
    }

}
