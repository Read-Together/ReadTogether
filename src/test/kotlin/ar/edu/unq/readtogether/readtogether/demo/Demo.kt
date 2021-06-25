package ar.edu.unq.readtogether.readtogether.demo

import ar.edu.unq.readtogether.readtogether.modelo.Grupo
import ar.edu.unq.readtogether.readtogether.modelo.Libro
import ar.edu.unq.readtogether.readtogether.modelo.Usuario
import ar.edu.unq.readtogether.readtogether.repositories.GrupoRepository
import ar.edu.unq.readtogether.readtogether.repositories.UsuarioRepository
import ar.edu.unq.readtogether.readtogether.services.GrupoService
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class Demo {

    @Autowired
    private lateinit var usuarioService : UsuarioService
    @Autowired
    private lateinit var grupoService : GrupoService


    @Test
    fun cargaDeDatos(){
        val usuario1 = Usuario("gonzalo", "gonzalo@gmail.com", "123")
        val usuario2 = Usuario("barbi", "barbi@gmail.com", "123")
        val usuario3 = Usuario("mauro", "mauro@gmail.com", "123")
        val usuario4 = Usuario("juan", "juan@gmail.com", "123")

        usuarioService.registrarUsuario(usuario1)
        usuarioService.registrarUsuario(usuario2)
        usuarioService.registrarUsuario(usuario3)
        usuarioService.registrarUsuario(usuario4)

        val descripcion1 = "En esta comunidad vamos a poder compartir nuestras experiencias al leer los" +
                " libros de Tolkien"
        val descripcion2 = "Solo están permitidos los lectores de los libros de Harry Potter que se identifiquen" +
                " con la casa de Griffindor"
        val descripcion3 = "Vengan que acá comentamos todas las obras de Maria Elena Walsh"
        val descripcion4 = "Dentro de este grupo analizaremos al best-seller 1984, siempre ante la atenta mirada del Big Brother"
        val grupo1 = Grupo("La comunidad del anillo", descripcion1, mutableListOf("gonzalo","mauro"))
        val grupo2 = Grupo("Leones de Hogwartz", descripcion2, mutableListOf("barbi", "juan"))
        val grupo3 = Grupo("Habitantes de Pehuajó", descripcion3, mutableListOf("barbi"))
        val grupo4 = Grupo("El ojo que todo lo vé", descripcion4, mutableListOf("barbi", "mauro"))

        /** LIBROS DE TOLKIEN **/
        val libroT1 = Libro("El Señor de los anillos. La comunidad del Anillo", "JRR Tolkien",
            "https://www.descargaLibrosGratis.com/ElSeñorDeLosAnillosLaComunidadDelAnillo")
        val libroT2 = Libro("El Señor de los anillos. Las Dos Torres", "JRR Tolkien",
            "https://www.descargaLibrosGratis.com/ElSeñorDeLosAnillosLasDosTorres")
        val libroT3 = Libro("El Señor de los anillos. El Retorno del Rey", "JRR Tolkien",
            "https://www.descargaLibrosGratis.com/ElSeñorDeLosAnillosElRetornoDelRey")
        val libroT4 = Libro("El Hobbit", "JRR Tolkien",
            "https://www.descargaLibrosGratis.com/ElHobbit")

        /** LIBROS DE HARRY POTTER **/
        val libroHp1 = Libro("Harry Potter y la Piedra filosofal", "JK Rowling",
            "https://www.descargaLibrosGratis.com/HarryPotterYLaPiedraFilosofal")
        val libroHp2 = Libro("Harry Potter y la camara secreta", "JK Rowling",
            "https://www.descargaLibrosGratis.com/HarryPotterYLaCamaraSecreta")
        val libroHp3 = Libro("Harry Potter y el prisionero de Azkaban", "JK Rowling",
            "https://www.descargaLibrosGratis.com/HarryPotterYElPrisioneroDeAzkaban")
        val libroHp4 = Libro("Harry Potter y el Caliz de Fuego", "JK Rowling",
            "https://www.descargaLibrosGratis.com/HarryPotterYElCalizDeFuego")
        val libroHp5 = Libro("Harry Potter y la Orden del Fenix", "JK Rowling",
            "https://www.descargaLibrosGratis.com/HarryPotterYLaOrdenDelFenix")
        val libroHp6 = Libro("Harry Potter y el Misterio del Principe", "JK Rowling",
            "https://www.descargaLibrosGratis.com/HarryPotterYElMisterioDelPrincipe")
        val libroHp7 = Libro("Harry Potter y las reliquias de la muerte", "JK Rowling",
            "https://www.descargaLibrosGratis.com/HarryPotterYLasReliqueasDeLaMuerte")

        /** LIBROS DE MARIA ELENA WALSH **/
        val libroM1 = Libro("El Reino del Revés",
            "Maria Elena Walsh", "https://www.descargaLibrosGratis.com/ElReinoDelReves")
        val libroM2 = Libro("Zoo Loco", "Maria Elena Walsh", "https://www.descargaLibrosGratis.com/ZooLoco")
        val libroM3 = Libro("Doña Disparate y Bambuco", "Maria Elena Walsh",
            "https://www.descargaLibrosGratis.com/DonaDisparateYBambuco")
        val libroM4 = Libro("Cuentopos de Gulubú", "Maria Elena Walsh",
            "https://www.descargaLibrosGratis.com/CuentoposDeGulubu")
        val libroM5 = Libro("La Nube Traicionera", "Maria Elena Walsh",
            "https://www.descargaLibrosGratis.com/LaNubeTraicionera")

        /** 1984 **/
        val libroGO = Libro("1984", "George Orwell", "https://www.descargaLibrosGratis.com/1984")

        grupoService.guardarGrupo(grupo1)
        grupoService.guardarGrupo(grupo2)
        grupoService.guardarGrupo(grupo3)
        grupoService.guardarGrupo(grupo4)

        val idGrupo1 = grupo1.id
        grupoService.cargarLibro(idGrupo1, libroT1)
        grupoService.cargarLibro(idGrupo1, libroT2)
        grupoService.cargarLibro(idGrupo1, libroT3)
        grupoService.cargarLibro(idGrupo1, libroT4)

        val idGrupo2 = grupo2.id
        grupoService.cargarLibro(idGrupo2, libroHp1)
        grupoService.cargarLibro(idGrupo2, libroHp2)
        grupoService.cargarLibro(idGrupo2, libroHp3)
        grupoService.cargarLibro(idGrupo2, libroHp4)
        grupoService.cargarLibro(idGrupo2, libroHp5)
        grupoService.cargarLibro(idGrupo2, libroHp6)
        grupoService.cargarLibro(idGrupo2, libroHp7)

        val idGrupo3 = grupo3.id
        grupoService.cargarLibro(idGrupo3, libroM1)
        grupoService.cargarLibro(idGrupo3, libroM2)
        grupoService.cargarLibro(idGrupo3, libroM3)
        grupoService.cargarLibro(idGrupo3, libroM4)
        grupoService.cargarLibro(idGrupo3, libroM5)

        val idGrupo4 = grupo4.id
        grupoService.cargarLibro(idGrupo4, libroGO)
    }
}