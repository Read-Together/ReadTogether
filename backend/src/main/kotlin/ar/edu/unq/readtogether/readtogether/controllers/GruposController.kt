package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class GruposController {

    @Autowired
    private lateinit var grupoRepository: GrupoRepository

    @GetMapping("/grupos")
    fun buscarGrupos(@RequestParam busqueda : String): List<String> {
        return grupoRepository.obtenerGrupos(busqueda)
    }
}