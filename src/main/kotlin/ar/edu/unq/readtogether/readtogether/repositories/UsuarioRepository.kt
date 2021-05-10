package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.dtos.UsuarioResponseDTO
import ar.edu.unq.readtogether.readtogether.firebase.FirebaseInitialization
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import kotlin.jvm.Throws

@Repository
class UsuarioRepository {

    @Autowired
    private lateinit var firebase : FirebaseInitialization

    private fun getCollection() = firebase.getFirestore().collection("usuarios")

    @Throws
    fun registrarUsuario(usuario: Usuario) : UsuarioResponseDTO? {
        var docData: MutableMap<String, Any> = mutableMapOf()
        docData["userName"] = usuario.userName
        docData["email"] = usuario.email
        docData["password"] = usuario.password
        var users = getCollection()
        validarUsuario(usuario)
        users.document().create(docData)
        return buscarUsuario(usuario)

    }

    private fun validarUsuario(usuario: Usuario) {
        if (buscarUsuario(usuario) != null || !emailValido(usuario.email)) {
            throw RuntimeException()
        }
    }

    /** TODO Estoy queriendo hacer dos cosas al mismo tiempo, buscar el usuario y comprobar que no existe**/


    fun buscarUsuario(usuario: Usuario) : UsuarioResponseDTO?{
        var userName = getCollection().whereEqualTo("userName", usuario.userName).get().get()
        var userEmail = getCollection().whereEqualTo("email", usuario.email).get().get()
        if(userName.isEmpty && userEmail.isEmpty){
            return null
        }
        var usuarios: MutableList<UsuarioResponseDTO> = when(userName.isEmpty){
            false -> userName.toObjects(UsuarioResponseDTO::class.java)
            true -> userEmail.toObjects(UsuarioResponseDTO::class.java)
        }
        var retorno = usuarios[0]
        retorno.id = usuarios[0].id
        return retorno
    }

    fun emailValido(email:String): Boolean{
        return email.contains("@gmail.com") || email.contains("@hotmail.com")
    }

    fun eliminarDatos(){
        var collection = getCollection().listDocuments()
        for (doc in collection){
            doc.delete()
        }
    }
}