package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.dtos.UsuarioResponseDTO
import ar.edu.unq.readtogether.readtogether.excepciones.CredencialesDeLoginInvalidasException
import ar.edu.unq.readtogether.readtogether.firebase.FireBaseInitialization
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.security.Token
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UsuarioRepository {

    @Autowired
    private lateinit var firebase : FireBaseInitialization
    private fun getCollection() = firebase.firestore.collection("usuarios")

    @Throws
    fun registrarUsuario(usuario: Usuario): UsuarioResponseDTO? {
        var docData: MutableMap<String, Any> = mutableMapOf()
        docData["userName"] = usuario.userName
        docData["email"] = usuario.email
        docData["password"] = usuario.password
        var users = getCollection()
        validarUsuario(usuario)
        users.document().set(docData)
        return buscarUsuario(usuario)

    }

    private fun validarUsuario(usuario: Usuario) {
        if (buscarUsuario(usuario) != null) {
            throw RuntimeException("El usuario ya existe")
        } else if (!emailValido(usuario.email)) {
            throw RuntimeException("El email no es valido")
        }
    }

    /** TODO Estoy queriendo hacer dos cosas al mismo tiempo, buscar el usuario y comprobar que no existe**/


    fun buscarUsuario(usuario: Usuario) : UsuarioResponseDTO?{
        var userName = getCollection().whereEqualTo("userName", usuario.userName).get().get()
        var userEmail = getCollection().whereEqualTo("email", usuario.email).get().get()
        if(userName.isEmpty && userEmail.isEmpty){
            return null
        }
        var usuarios: MutableList<UsuarioResponseDTO> = if(userName.isEmpty){
            userEmail.toObjects(UsuarioResponseDTO::class.java)
        }else{
            userName.toObjects(UsuarioResponseDTO::class.java)
        }
        var retorno = usuarios[0]
        retorno.id = usuarios[0].id
        return retorno
    }


    fun emailValido(email:String): Boolean{
        return email.contains("@") || email.contains(".com")
    }

    fun eliminarDatos(){
        var collection = getCollection().listDocuments()
        for (doc in collection){
            doc.delete()
        }
    }

    fun usuarioDeNombre(usuario: String): Usuario {
        val userName = getCollection().whereEqualTo("userName", usuario).get().get()
        return userName.toObjects(Usuario::class.java)[0]
    }
    /**
     * Retorna un token con el prefijo "Bearer"
     * "Bearer " + token
     * **/
    fun login(usuario: String, password: String): String {
        var userName = getCollection().whereEqualTo("userName", usuario).get().get()
        if(!userName.isEmpty){
            val passDB = userName.toObjects(Usuario::class.java)
            if(passwordEsCorrecta(passDB[0].password, password )){
                return Token.getJWTToken(passDB[0].userName)
            }
            throw CredencialesDeLoginInvalidasException()
        }else{
            throw CredencialesDeLoginInvalidasException()
        }
    }

    fun passwordEsCorrecta(passDB: String, passLogin: String): Boolean{
        return passDB == passLogin
    }
}