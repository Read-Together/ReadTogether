package ar.edu.unq.readtogether.readtogether.usuarios

class Usuario {
    lateinit var userName: String
    lateinit var email: String
    lateinit var password: String

    constructor(userName: String, email: String, password: String){
        this.userName = userName
        this.email = email
        this.password = password
    }

    constructor()
}