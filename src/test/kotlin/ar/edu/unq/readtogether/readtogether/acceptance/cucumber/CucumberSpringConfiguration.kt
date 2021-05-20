package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@CucumberContextConfiguration
@SpringBootTest(classes = [MockMvc::class, UsuarioService::class])
class CucumberSpringConfiguration {
}