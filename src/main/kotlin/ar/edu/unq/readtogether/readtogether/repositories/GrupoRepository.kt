package ar.edu.unq.readtogether.readtogether.repositories

import org.springframework.stereotype.Repository

@Repository
class GrupoRepository{

    var library = mutableListOf<String>()

    fun save(grupo: String) {
        library.add(grupo)
    }

    fun obtenerGrupos(): List<String> {
        return library
    }

}
