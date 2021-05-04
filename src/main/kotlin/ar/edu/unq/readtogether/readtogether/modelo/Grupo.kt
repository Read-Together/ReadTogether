package ar.edu.unq.readtogether.readtogether.grupos

class Grupo{
    lateinit var id: String
    lateinit var nombre: String
    lateinit var descripcion: String

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