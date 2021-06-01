package ar.edu.unq.readtogether.readtogether.modelo

class Libro {
    lateinit var nombre:String
    lateinit var autores:MutableList<String>
    lateinit var link:String

    constructor(nombre:String, autores:MutableList<String>, link:String){
        this.nombre = nombre
        this.autores = autores
        this.link = link
    }

    constructor()
}
