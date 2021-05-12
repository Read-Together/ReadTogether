package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.dtos.UsuarioResponseDTO
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class UsuariosController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @PostMapping("/registrar")
    fun registrarUsuario(@RequestBody usuario: Usuario) : ResponseEntity<UsuarioResponseDTO?>{
        try {
            return ResponseEntity(usuarioService.registrarUsuario(usuario), HttpStatus.OK)
        } catch (e: RuntimeException) {
            println(e.stackTrace)
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
}