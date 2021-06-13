package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class SpringIntegrationTest {
    val username = "unNombreDeUsuario"
}