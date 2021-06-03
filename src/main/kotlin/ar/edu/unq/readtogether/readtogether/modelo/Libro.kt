package ar.edu.unq.readtogether.readtogether.modelo

class Libro {
    lateinit var nombre:String
    lateinit var autor:String
    lateinit var link:String

    constructor(nombre:String, autor:String, link:String){
        this.nombre = nombre
        this.autor = autor
        this.link = link
    }

    constructor()
}
