package ar.edu.unq.readtogether.readtogether.repositories

import ar.edu.unq.readtogether.readtogether.dtos.RequestUsuario
import ar.edu.unq.readtogether.readtogether.dtos.UsuarioResponseDTO
import ar.edu.unq.readtogether.readtogether.firebase.FireBaseInitialization
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.security.Token
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository


@Repository
class UsuarioRepository {

    @Autowired
    private lateinit var firebase : FireBaseInitialization
    private val token = Token()
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
        var usuarios: MutableList<UsuarioResponseDTO> = mutableListOf()
	if(userName.isEmpty){
		usuarios = userEmail.toObjects(UsuarioResponseDTO::class.java)
	}else{
		usuarios = userName.toObjects(UsuarioResponseDTO::class.java)
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

    /**
     * Retorna un token con el prefijo "Bearer"
     * "Bearer " + token
     * **/
    fun login(usuario: RequestUsuario): String {
        var userName = getCollection().whereEqualTo("userName", usuario.usuario).get().get()
        if(!userName.isEmpty){
            val passDB = userName.toObjects(Usuario::class.java)
            if(passwordEsCorrecta(passDB[0].password, usuario.password )){
                return token.getJWTToken(passDB[0].userName)
            }
            throw Exception("Usuario y/o contraseña inválidos")
        }else{
            throw Exception("Usuario y/o contraseña inválidos")
        }
    }

    fun passwordEsCorrecta(passDB: String, passLogin: String): Boolean{
        return passDB == passLogin
    }
}