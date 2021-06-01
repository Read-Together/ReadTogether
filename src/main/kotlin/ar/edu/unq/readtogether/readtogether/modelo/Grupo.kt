package ar.edu.unq.readtogether.readtogether.modelo

import java.util.*

class Grupo{

    fun agregarUsuario(usuario: String) {
        usuarios.add(usuario)
    }


    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String
    lateinit var usuarios: MutableList<String>

    constructor(nombre: String, descripcion: String, usuarios: MutableList<String>){
        this.id = UUID.randomUUID().toString()
        this.nombre = nombre
        this.descripcion = descripcion
        this.usuarios = usuarios
    }

    constructor()

}