package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.dtos.UsuarioResponseDTO
import ar.edu.unq.readtogether.readtogether.repositories.UsuarioRepository
import ar.edu.unq.readtogether.readtogether.usuarios.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    fun registrarUsuario(usuario: Usuario) : UsuarioResponseDTO?{
        return usuarioRepository.registrarUsuario(usuario)
    }

    fun eliminarDatos(){
        return usuarioRepository.eliminarDatos()
    }
}