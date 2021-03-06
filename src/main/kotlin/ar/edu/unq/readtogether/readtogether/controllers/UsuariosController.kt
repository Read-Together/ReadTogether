package ar.edu.unq.readtogether.readtogether.controllers

import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.dtos.UsuarioResponseDTO
import ar.edu.unq.readtogether.readtogether.excepciones.CredencialesDeLoginInvalidasException
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
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
    fun registrarUsuario(@RequestBody usuario: Usuario): ResponseEntity<UsuarioResponseDTO?> {
        return try {
            ResponseEntity(usuarioService.registrarUsuario(usuario), HttpStatus.OK)
        } catch (e: RuntimeException) {
            println(e.printStackTrace())
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: RequestUsuario): String{
        return usuarioService.login(usuario)
    }

    @ExceptionHandler(value = [(CredencialesDeLoginInvalidasException::class)])
    fun manejarFalloDeCredenciales(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.FORBIDDEN)
    }
}