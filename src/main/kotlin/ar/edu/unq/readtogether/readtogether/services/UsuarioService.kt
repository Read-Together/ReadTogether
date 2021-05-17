package ar.edu.unq.readtogether.readtogether.services

import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.dtos.UsuarioResponseDTO
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService{

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    fun registrarUsuario(usuario: Usuario) : UsuarioResponseDTO?{
        return usuarioRepository.registrarUsuario(usuario)
    }

    fun buscarUsuario(usuario: Usuario):UsuarioResponseDTO?{
        return usuarioRepository.buscarUsuario(usuario)
    }

    fun eliminarDatos(){
        return usuarioRepository.eliminarDatos()
    }

    fun login(usuario: RequestUsuario): String {
        return usuarioRepository.login(usuario)
    }

}

