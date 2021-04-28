package ar.edu.unq.readtogether.readtogether.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GruposController {

    @GetMapping("/grupos")
    fun buscarGrupos(): List<String> {
        return listOf<String>("El señor de los anillos")
    }
}