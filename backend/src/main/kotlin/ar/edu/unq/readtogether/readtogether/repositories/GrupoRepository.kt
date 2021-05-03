package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.firebase.FirebaseInitialization
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class GrupoRepository{

    var library = mutableListOf<String>("La comunidad del anillo", "Fans de Percy Jackson")

    @Autowired
    private lateinit var firebase : FirebaseInitialization

    private fun getCollection() = firebase.getFirestore().collection("groups")

    fun save(grupo: String) {
        library.add(grupo)
    }

    fun obtenerGrupos(terminoDeBusqueda: String): List<String> {
        return library.filter { grupo -> grupo.contains(terminoDeBusqueda, ignoreCase = true) }
    }

    /***
     * Para probar la base cree una colección llamada group. Si tengo el visto bueno de todes,
     * cambio tod para que "save" persista en la base y "obtenerGrupos" los pida tambien.
     */
    fun retornarTodosLosGruposFirebase() : List<Grupo>{
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


    fun guardarGrupoFirebase(grupo: Grupo) : Boolean{
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
}
