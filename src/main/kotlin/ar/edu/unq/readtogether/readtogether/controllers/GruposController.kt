package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GruposController {

    @Autowired
    private lateinit var grupoRepository: GrupoRepository

    @GetMapping("/grupos")
    fun buscarGrupos(): List<String> {
        return grupoRepository.obtenerGrupos()
    }
}