package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@CrossOrigin
@RestController
class GruposController {

    @Autowired
    private lateinit var grupoService: GrupoService
    @GetMapping("/grupos")
    fun buscarGrupos(@RequestParam busqueda : String): List<Grupo> {
        return grupoService.obtenerGruposConNombre(busqueda)
    }

}