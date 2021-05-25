package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.firebase.FireBaseInitialization
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import com.google.api.core.ApiFuture
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class GrupoRepository {

    @Autowired
    private lateinit var firebase: FireBaseInitialization

    private fun getCollection() = firebase.firestore.collection("grupos")

    fun save(grupo: Grupo): String {
        var docData: MutableMap<String, Any> = mutableMapOf()
        docData["nombre"] = grupo.nombre
        docData["descripcion"] = grupo.descripcion
        docData["usuarios"] = grupo.usuarios
        docData["id"] = grupo.id
        var groups = getCollection()
        var writeResultApiFuture: ApiFuture<WriteResult> = groups.document().set(docData)
        try {
            if (writeResultApiFuture.get() != null) {
                return "El grupo ${grupo.nombre} ha sido creado"
            }
            return "El grupo ${grupo.nombre} ha sido creado"
        } catch (e: Exception) {
            return "El grupo ${grupo.nombre} NO ha sido creado"
        }
    }

    fun obtenerGrupos(terminoDeBusqueda: String): List<Grupo> {
        var retorno = mutableListOf<Grupo>()
        lateinit var grupo: Grupo
        var collection: ApiFuture<QuerySnapshot> = getCollection().get()
        for (doc in collection.get().documents) {
            grupo = doc.toObject(Grupo::class.java)
            retorno.add(grupo)
        }
        return retorno.filter { it.nombre.contains(terminoDeBusqueda) || it.descripcion.contains(terminoDeBusqueda) }
    }

    /***metodo de prueba ***/
    fun eliminarDatos() {
        getCollection().listDocuments().forEach{each -> each.delete()}
    }

    fun obtenerGrupoDeID(idDelGrupo: String): Grupo {
        return getCollection().get().get().documents.map { each -> crearGrupoDesde(each) }
            .find { each -> each.id == idDelGrupo }!!
    }

    fun actualizarGrupo(grupo: Grupo) {
        getCollection().get().get().documents.first { each -> each.data["id"] == grupo.id }.reference.update(
            mutableMapOf(
                Pair("id", grupo.id),
                Pair("nombre", grupo.nombre),
                Pair("descripcion", grupo.descripcion),
                Pair("usuarios", grupo.usuarios)
            )
        )
    }

    private fun crearGrupoDesde(grupo: QueryDocumentSnapshot?): Grupo {
        val grupoCreado = grupo!!.toObject(Grupo::class.java)
        //grupoCreado.id = grupo.id
        return grupoCreado
    }

}
