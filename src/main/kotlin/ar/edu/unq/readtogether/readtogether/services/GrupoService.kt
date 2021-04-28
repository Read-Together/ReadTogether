package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GrupoService {

    @Autowired
    private lateinit var grupoRepository: GrupoRepository

    fun guardarGrupo(grupo: String) {
        grupoRepository.save(grupo)
    }

}
