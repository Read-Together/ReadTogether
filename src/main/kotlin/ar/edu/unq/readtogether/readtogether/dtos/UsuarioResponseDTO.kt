package ar.edu.unq.readtogether.readtogether.dtos

data class UsuarioResponse(var id: String?, var userName: String, var email: String)

class UsuarioResponseDTO {

    var id: String? = null
    lateinit var userName: String
    lateinit var email: String


    constructor(id: String, userName: String, email: String){
        this.id = id
        this.userName = userName
        this.email = email
    }

    constructor()
}