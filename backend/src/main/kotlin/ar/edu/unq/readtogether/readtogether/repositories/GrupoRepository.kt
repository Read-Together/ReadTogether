package ar.edu.unq.readtogether.readtogether.repositories

import org.springframework.stereotype.Repository

@Repository
class GrupoRepository{

    var library = mutableListOf<String>("La comunidad del anillo", "Fans de Percy Jackson")

    fun save(grupo: String) {
        library.add(grupo)
    }

    fun obtenerGrupos(terminoDeBusqueda: String): List<String> {
        return library.filter { grupo -> grupo.contains(terminoDeBusqueda, ignoreCase = true) }
    }

}
