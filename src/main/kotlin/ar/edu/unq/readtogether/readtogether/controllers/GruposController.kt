package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.dtos.RequestDeSuscripcion
import ar.edu.unq.readtogether.readtogether.grupos.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class GruposController {

    @Autowired
    private lateinit var grupoService: GrupoService

    @GetMapping("/grupos")
    fun buscarGrupos(@RequestParam busqueda : String): List<Grupo> {
        return grupoService.obtenerGruposConNombre(busqueda)
    }

    @PostMapping("/grupos")
    fun crearGrupo(@RequestBody creacionDeGruposForm: CreacionDeGruposForm){
        grupoService.crearGrupo(creacionDeGruposForm.nombre,creacionDeGruposForm.descripcion)
    }

    @PostMapping("/grupos/{nombreDeGrupo}/registrar")
    fun suscribirUsuarioAlGrupo(@RequestBody usuario: RequestDeSuscripcion,@PathVariable nombreDeGrupo:String){
        grupoService.suscribirUsuarioAlGrupo(usuario.userName,nombreDeGrupo)
    }

}