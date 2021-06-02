package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.dtos.RequestConUsername
import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class GruposController {

    @Autowired
    private lateinit var grupoService: GrupoService

    @GetMapping("/grupos")
    fun buscarGrupos(@RequestParam busqueda : String): ResponseEntity<List<Grupo>> {
        return ResponseEntity(grupoService.obtenerGruposConNombre(busqueda),HttpStatus.ACCEPTED)
    }

    @PostMapping("/grupos")
    fun crearGrupo(@RequestBody creacionDeGruposForm: CreacionDeGruposForm): ResponseEntity<String>{
        return ResponseEntity(grupoService.crearGrupo(creacionDeGruposForm.nombre,creacionDeGruposForm.descripcion), HttpStatus.CREATED)
    }

    @PostMapping("/grupos/{idDelGrupo}/registrar")
    fun suscribirUsuarioAlGrupo(@PathVariable("idDelGrupo") idDelGrupo: String, @RequestBody usuario: RequestConUsername){
        grupoService.suscribirUsuarioAlGrupo(usuario.userName, idDelGrupo)
    }

    @PostMapping("/grupos/{idDelGrupo}/salir")
    fun salirDeComunidad(@PathVariable("idDelGrupo") idDelGrupo: String, @RequestBody usuario: RequestConUsername) : ResponseEntity<Void>{
        if (!coincideConUsuarioAutenticado(usuario)){
            return ResponseEntity(HttpStatus.FORBIDDEN)
        } else {
            try {
                grupoService.quitarUsuarioDelGrupo(usuario.userName, idDelGrupo)
                return ResponseEntity(HttpStatus.OK)
            } catch (e: RuntimeException) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        }
    }

    @GetMapping("/grupos/{idDelGrupo}")
    fun comunidad(@PathVariable("idDelGrupo") idDelGrupo: String) : ResponseEntity<Grupo>{
        return ResponseEntity(grupoService.obtenerGrupoDeID(idDelGrupo), HttpStatus.ACCEPTED)
    }

    private fun coincideConUsuarioAutenticado(usuario: RequestConUsername) =
            SecurityContextHolder.getContext().getAuthentication().name == usuario.userName
}