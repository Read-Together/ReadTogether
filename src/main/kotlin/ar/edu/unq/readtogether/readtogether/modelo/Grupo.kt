package ar.edu.unq.readtogether.readtogether.grupos

import ar.edu.unq.readtogether.readtogether.modelo.Usuario

class Grupo{

    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }


    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var usuarios: MutableList<Usuario>

    constructor(id: String, nombre: String, descripcion: String, usuarios: MutableList<Usuario>){
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.usuarios = usuarios
    }

    constructor()

    constructor(nombre: String, descripcion: String, usuarios: MutableList<Usuario>){
        this.nombre = nombre
        this.descripcion = descripcion
        this.usuarios = usuarios
    }

}