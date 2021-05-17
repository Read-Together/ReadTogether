package ar.edu.unq.readtogether.readtogether.grupos

import ar.edu.unq.readtogether.readtogether.modelo.Usuario

class Grupo{

    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }


    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    var usuarios: MutableList<Usuario> = mutableListOf()

    constructor(id: String, nombre: String, descripcion: String){
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
    }

    constructor()

    constructor(nombre: String, descripcion: String){
        this.nombre = nombre
        this.descripcion = descripcion
    }

}