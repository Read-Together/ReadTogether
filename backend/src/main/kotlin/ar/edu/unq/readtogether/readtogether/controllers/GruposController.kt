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
    private lateinit var grupoRepository: GrupoRepository

    /*** Servicio creado para probar la base de datos ***/
    @Autowired
    private val grupoService = GrupoService()

    @GetMapping("/grupos")
    fun buscarGrupos(@RequestParam busqueda : String): List<String> {
        return grupoRepository.obtenerGrupos(busqueda)
    }

    /***
     * Mapeos creados para aprender como usar la base de datos.
     * Cuando tenga el visto bueno de todes, modifico para que tod encaje
     */
    @GetMapping("/get")
    fun grupos() : ResponseEntity<List<Grupo>>{
        return ResponseEntity(grupoService.retornarTodosLosGrupos(), HttpStatus.OK)
    }

    /*** Metodo creado para probar labase de datos ***/
    @PostMapping("/add")
    fun guardarGrupo(@RequestBody grupo: Grupo) : ResponseEntity<Boolean> {
        return ResponseEntity(grupoService.guardarGrupoFirebase(grupo), HttpStatus.OK)
    }
}