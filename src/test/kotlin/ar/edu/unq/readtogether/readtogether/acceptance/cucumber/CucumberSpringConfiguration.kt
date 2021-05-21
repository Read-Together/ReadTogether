package ar.edu.unq.readtogether.readtogether.acceptance.cucumber

import ar.edu.unq.readtogether.readtogether.firebase.FireBaseInitialization
import ar.edu.unq.readtogether.readtogether.repositories.UsuarioRepository
import ar.edu.unq.readtogether.readtogether.services.UsuarioService
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@CucumberContextConfiguration
@SpringBootTest(classes = [UsuarioService::class,
    UsuarioRepository::class,
    FireBaseInitialization::class,
    StepDefinitionsContext::class,
    MockMvc::class])
class CucumberSpringConfiguration {
}