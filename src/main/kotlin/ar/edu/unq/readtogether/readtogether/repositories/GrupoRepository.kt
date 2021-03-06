package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.excepciones.EntidadNoEncontradaException
import ar.edu.unq.readtogether.readtogether.firebase.FireBaseInitialization
import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Libro
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
        docData["biblioteca"] = grupo.biblioteca
        var groups = getCollection()
        var writeResultApiFuture: ApiFuture<WriteResult> = groups.document().set(docData)
        if (writeResultApiFuture.get() != null) {
            return grupo.id
        } else {
            throw Exception("El grupo ${grupo.nombre} NO ha sido creado")
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
        getCollection().listDocuments().forEach { each -> each.delete() }
    }

    fun obtenerGrupoDeID(idDelGrupo: String): Grupo {
        return getCollection().get().get().documents.map { each -> crearGrupoDesde(each) }
                .find { each -> each.id == idDelGrupo }
                ?: throw EntidadNoEncontradaException("No se encontr?? el grupo con id ${idDelGrupo}")
    }

    fun actualizarGrupo(grupo: Grupo) {
        val grupoEncontrado = getCollection().get().get().documents.first { each -> each.data["id"]!!.equals(grupo.id) }
        val camposActualizados = mutableMapOf(
                Pair("nombre", grupo.nombre),
                Pair("descripcion", grupo.descripcion),
                Pair("usuarios", grupo.usuarios),
                Pair("biblioteca", grupo.biblioteca)
            )
        ejecutarActualizacion(grupoEncontrado, camposActualizados)
    }

    private fun crearGrupoDesde(grupo: QueryDocumentSnapshot?): Grupo {
        val grupoCreado = grupo!!.toObject(Grupo::class.java)
        return grupoCreado
    }

    private fun ejecutarActualizacion(grupoEncontrado: QueryDocumentSnapshot, camposActualizados: MutableMap<String, Any>) {
        val result = grupoEncontrado.reference.update(camposActualizados)
        if (result.get() == null) {
            throw RuntimeException("No se pudo actualizar el grupo")
        }
    }

    fun obtenerBibliotecaDeGrupo(idDelGrupo: String): MutableList<Libro> {
        val grupo = obtenerGrupoDeID(idDelGrupo)
        return grupo.biblioteca
    }

    fun gruposDelUsuario(nombreUsuario: String): List<Grupo> {
        var grupos = getCollection().whereArrayContains("usuarios", nombreUsuario).get().get()
        var listaDeGrupos = mutableListOf<Grupo>()
        for(grupo in grupos){
            listaDeGrupos.add(crearGrupoDesde(grupo))
        }
        return listaDeGrupos.toList()
    }

}
