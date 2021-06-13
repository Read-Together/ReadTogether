package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.dtos.CreacionDeGruposForm
import ar.edu.unq.readtogether.readtogether.dtos.RequestConUsername
import ar.edu.unq.readtogether.readtogether.excepciones.EntidadNoEncontradaException
import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Libro
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
        return if (!coincideConUsuarioAutenticado(usuario)) {
            ResponseEntity(HttpStatus.FORBIDDEN)
        } else {
            try {
                grupoService.quitarUsuarioDelGrupo(usuario.userName, idDelGrupo)
                ResponseEntity(HttpStatus.OK)
            } catch (e: RuntimeException) {
                ResponseEntity(HttpStatus.BAD_REQUEST)
            }
        }
    }

    @GetMapping("/grupos/{idDelGrupo}")
    fun comunidad(@PathVariable("idDelGrupo") idDelGrupo: String) : ResponseEntity<Grupo>{
        return ResponseEntity(grupoService.obtenerGrupoDeID(idDelGrupo), HttpStatus.ACCEPTED)
    }

    private fun coincideConUsuarioAutenticado(usuario: RequestConUsername) =
            SecurityContextHolder.getContext().getAuthentication().name == usuario.userName

    @GetMapping("/grupos/{idDelGrupo}/biblioteca")
    fun biblioteca(@PathVariable("idDelGrupo") idDelGrupo: String) : ResponseEntity<MutableList<Libro>>{
        return ResponseEntity(grupoService.obtenerBibiliotecaDeGrupo(idDelGrupo), HttpStatus.ACCEPTED)
    }

    @PostMapping("/grupos/{idDelGrupo}/biblioteca")
    fun cargarLibro(@PathVariable("idDelGrupo")idDelGrupo: String, @RequestBody libro: Libro): ResponseEntity<Unit> {
        try {
            return ResponseEntity(grupoService.cargarLibro(idDelGrupo, libro), HttpStatus.OK)
        } catch (e: EntidadNoEncontradaException) {
            println(e.printStackTrace())
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
}